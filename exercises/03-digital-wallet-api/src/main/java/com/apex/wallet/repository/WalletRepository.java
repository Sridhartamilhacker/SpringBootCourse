package com.apex.wallet.repository;

import com.apex.wallet.model.Wallet;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findById( @NonNull UUID id);
    List<Wallet> findWalletsByUserId(UUID id);
    boolean isWalletAvailable(UUID id);
}
