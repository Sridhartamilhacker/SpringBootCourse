package com.apex.wallet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "ledger_entries")
public class LedgerEntry {
    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    @Column(name = "entry_type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private EntryType entryType;
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount = BigDecimal.ZERO;
    @Column(name = "running_balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal runningBalance = BigDecimal.ZERO;
    @Column(name = "created_at",nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    public LedgerEntry(){}
}
