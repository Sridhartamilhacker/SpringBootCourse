package com.apex.wallet.controller;

import com.apex.wallet.dto.*;
import com.apex.wallet.model.IdempotencyRecord;
import com.apex.wallet.model.Transaction;
import com.apex.wallet.service.IdempotencyService;
import com.apex.wallet.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PaymentController {
    private final IdempotencyService idempotencyService;
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @PostMapping("/payments/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestHeader(value = "idempotency-key" , required = false) String idempotencyKey,
                                               @Valid @RequestBody DepositRequest req){
        ResponseEntity<TransactionResponse> record = checkCachedResponse(idempotencyKey);
        if (record != null) return record;
        Transaction newTx = paymentService.deposit(req.targetWalletNumber(),req.amount(),req.referenceNote());
        TransactionResponse response = TransactionResponse.fromEntity(newTx);

        if(idempotencyKey != null){
            idempotencyService.saveRecord(idempotencyKey,newTx, 201, response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/payments/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestHeader(value = "idempotency-key", required = false) String idempotencyKey,
    @Valid @RequestBody WithdrawRequest req){
        ResponseEntity<TransactionResponse> record = checkCachedResponse(idempotencyKey);
        if (record != null) return record;

        Transaction newTx = paymentService.withdraw(req.sourceWalletNumber(),req.amount(),req.referenceNote());
        TransactionResponse response = TransactionResponse.fromEntity(newTx);

        if(idempotencyKey != null){
            idempotencyService.saveRecord(idempotencyKey,newTx,201,response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @PostMapping("/payments/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestHeader(value = "idempotency-key", required = false) String idempotencyKey,
                                                        @Valid @RequestBody TransferRequest req){
        ResponseEntity<TransactionResponse> record = checkCachedResponse(idempotencyKey);
        if(record != null) return record;
        Transaction newTx = paymentService.transfer(req.sourceWalletNumber(),req.targetWalletNumber(),req.amount(),req.referenceNote());
        TransactionResponse response = TransactionResponse.fromEntity(newTx);
        if(idempotencyKey != null){
            idempotencyService.saveRecord(idempotencyKey,newTx,201,response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/wallets/{walletNumber}/statement")
    public ResponseEntity<List<LedgerEntryResponse>> statement(@PathVariable @NotNull String walletNumber){
        List<LedgerEntryResponse> statement = paymentService.getWalletStatement(walletNumber)
                .stream().map(LedgerEntryResponse::fromEntity).toList();
        return ResponseEntity.ok(statement);
    }
    private @Nullable ResponseEntity<TransactionResponse> checkCachedResponse(String idempotencyKey) {
        if(idempotencyKey != null){
            Optional<IdempotencyRecord> cached = idempotencyService.findRecord(idempotencyKey);
            if(cached.isPresent()){
                IdempotencyRecord record = cached.get();
                try {
                    TransactionResponse response = objectMapper.readValue(record.getResponseBody(), TransactionResponse.class);
                    return ResponseEntity.status(record.getResponseStatus()).body(response);
                }
                catch (Exception ex){
                    throw new RuntimeException("Failed to deserialize cached idempotency response", ex);
                }
            }
        }
        return null;
    }
}
