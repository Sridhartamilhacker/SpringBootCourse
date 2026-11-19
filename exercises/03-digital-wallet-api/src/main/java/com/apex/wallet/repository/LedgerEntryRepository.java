package com.apex.wallet.repository;

import com.apex.wallet.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, UUID> {
    List<LedgerEntry> findByWallet_IdOrderByCreatedAtDesc(UUID walletId);
    List<LedgerEntry> findByTransaction_Id(UUID transactionId);
}
