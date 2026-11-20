package com.apex.wallet.dto;

import com.apex.wallet.model.Transaction;
import com.apex.wallet.model.TransactionStatus;
import com.apex.wallet.model.TransactionType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        TransactionType transactionType,
        TransactionStatus transactionStatus,
        String referenceNote,
        OffsetDateTime createdAt
) {
    public static TransactionResponse fromEntity(Transaction transaction){
        return new TransactionResponse(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getTransactionStatus(),
                transaction.getReferenceNote(),
                transaction.getCreatedAt()
        );
    }
}
