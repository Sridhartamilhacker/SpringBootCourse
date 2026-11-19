package com.apex.wallet.repository;

import com.apex.wallet.model.Wallet;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findByWalletNumber(String walletNumber);
    List<Wallet> findByUser_Id(UUID userId);
    boolean existsById(@NonNull UUID id);
}
