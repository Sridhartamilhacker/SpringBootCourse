package com.apex.wallet.dto;

import com.apex.wallet.model.Wallet;
import com.apex.wallet.model.WalletStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        UUID userId,
        String walletNumber,
        String currency,
        BigDecimal balance,
        WalletStatus status,
        OffsetDateTime createdAt
) {
    public static WalletResponse fromEntity(Wallet wallet){
        return new WalletResponse(
                wallet.getId(),
                wallet.getUser() != null ? wallet.getUser().getId() : null,
                wallet.getWalletNumber(),
                wallet.getCurrency(),
                wallet.getBalance(),
                wallet.getStatus(),
                wallet.getCreatedAt());
    }
}
