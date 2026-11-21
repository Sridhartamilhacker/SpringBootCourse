package com.apex.wallet.controller;

import com.apex.wallet.dto.CreateWalletRequest;
import com.apex.wallet.dto.UpdateWalletStatusRequest;
import com.apex.wallet.dto.WalletResponse;
import com.apex.wallet.model.Wallet;
import com.apex.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/users/{userId}/wallets")
    public ResponseEntity<WalletResponse> createWallet(@PathVariable UUID userId,
                                                       @RequestBody @Valid CreateWalletRequest req){
        Wallet newWallet = walletService.createWallet(userId,req.currency());
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/wallets/{walletNumber}")
                .buildAndExpand(newWallet.getWalletNumber())
                .toUri();
        return ResponseEntity.created(location).body(WalletResponse.fromEntity(newWallet));
    }

    @GetMapping("/wallets/{walletNumber}")
    public ResponseEntity<WalletResponse> getWalletByNumber(@PathVariable String walletNumber){
        WalletResponse wallet =  WalletResponse.fromEntity(walletService.getWalletByNumber(walletNumber));
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/users/{userId}/wallets")
    public ResponseEntity<List<WalletResponse>> getAllWalletsForUser(@PathVariable UUID userId){
        List<Wallet> wallets = walletService.getWalletsByUser(userId);
        List<WalletResponse> walletList = wallets.stream().map(WalletResponse::fromEntity).toList();
        return ResponseEntity.ok(walletList);
    }

    @PatchMapping("/wallets/{walletNumber}/status")
    public ResponseEntity<WalletResponse> updateWalletStatus(@PathVariable String walletNumber, @Valid @RequestBody UpdateWalletStatusRequest req){
        Wallet updatedWallet = walletService.updateWalletStatus(walletNumber,req.status());
        return ResponseEntity.ok(WalletResponse.fromEntity(updatedWallet));
    }
}
