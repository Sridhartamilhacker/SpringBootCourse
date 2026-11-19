package com.apex.wallet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "idempotency_records")
public class IdempotencyRecord {
    @Id
    @Column(name = "idempotency_key", length = 128)
    private String idempotencyKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
    @Column(name = "response_status",nullable = false)
    private Integer responseStatus;
    @Column(name = "response_body", nullable = false, columnDefinition = "TEXT")
    private String responseBody;
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
    @Column(name = "expires_at", nullable = false, updatable = false)
    private OffsetDateTime expiredAt = createdAt.plusHours(24L);
}
