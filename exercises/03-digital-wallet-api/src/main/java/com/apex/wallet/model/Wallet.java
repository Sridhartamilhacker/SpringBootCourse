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
@Table(name = "wallets")
public class Wallet {
    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "wallet_number",unique = true, nullable = false, length = 50)
    private String walletNumber;
    @Column(length = 10, nullable = false)
    private String currency = "USD";
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;
    @Column(nullable = false,length = 20)
    @Enumerated(EnumType.STRING)
    private WalletStatus status = WalletStatus.ACTIVE;
    @Column(nullable = false)
    @Version
    private Long version = 0L;
    @Column(name = "created_at",nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
    @Column(name="updated_at")
    private OffsetDateTime updatedAt;

    public Wallet(){

    }
}
