package com.apex.wallet.repository;

import com.apex.wallet.dto.UserSummaryResponse;
import com.apex.wallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<UserSummaryResponse> findUserById(UUID id);

    @Query( value = """
    SELECT json_build_object(
        'id',u.id,
         'email',u.email,
         'walletCount',count(w.id)
            )::text
         FROM users u
         LEFT JOIN wallets w on w.user_id = u.id
         WHERE u.id = :userId
         GROUP BY u.id,u.email""", nativeQuery = true)
    String findUserNativeJson(@Param("userId") UUID userId);
}
