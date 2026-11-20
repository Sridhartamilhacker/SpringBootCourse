package com.apex.wallet.service;

import com.apex.wallet.exception.UserNotFoundException;
import com.apex.wallet.exception.WalletNotFoundException;
import com.apex.wallet.model.User;
import com.apex.wallet.model.Wallet;
import com.apex.wallet.model.WalletStatus;
import com.apex.wallet.repository.UserRepository;
import com.apex.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Transactional
    public Wallet createWallet(UUID userId, String currency){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID" + userId));
        Wallet newWallet = new Wallet();
        newWallet.setWalletNumber("WLT" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        newWallet.setUser(user);
        newWallet.setCurrency(currency);
        newWallet.setUpdatedAt(OffsetDateTime.now());
        walletRepository.save(newWallet);
        return newWallet;
    }
    public Wallet getWalletByNumber(String walletNumber){
        return walletRepository.findByWalletNumber(walletNumber)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with number: " + walletNumber));
    }
    public List<Wallet> getWalletsByUser(UUID userId){
        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException("User not found with ID" + userId);
        }
        return walletRepository.findByUser_Id(userId);
    }

    @Transactional
    public Wallet updateWalletStatus(String walletNumber, WalletStatus newStatus){
        Wallet updatedWallet = getWalletByNumber(walletNumber);
        updatedWallet.setStatus(newStatus);
        updatedWallet.setUpdatedAt(OffsetDateTime.now());
        walletRepository.save(updatedWallet);
        return updatedWallet;
    }
}
