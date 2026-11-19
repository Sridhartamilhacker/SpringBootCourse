package com.apex.wallet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(name = "transaction_type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Column(name = "reference_note")
    private String referenceNote;
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public Transaction(){}
}
