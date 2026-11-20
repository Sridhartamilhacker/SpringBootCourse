package com.apex.wallet.dto;

import com.apex.wallet.model.EntryType;
import com.apex.wallet.model.LedgerEntry;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LedgerEntryResponse(
        UUID id,
        UUID transactionId,
        UUID walletId,
        EntryType entryType,
        BigDecimal amount,
        BigDecimal runningTotal,
        OffsetDateTime createdAt
) {
    public static LedgerEntryResponse fromEntity(LedgerEntry ledgerEntry){
        return new LedgerEntryResponse(
                ledgerEntry.getId(),
                ledgerEntry.getTransaction().getId(),
                ledgerEntry.getWallet().getId(),
                ledgerEntry.getEntryType(),
                ledgerEntry.getAmount(),
                ledgerEntry.getRunningBalance(),
                ledgerEntry.getCreatedAt()
        );
    }
}
