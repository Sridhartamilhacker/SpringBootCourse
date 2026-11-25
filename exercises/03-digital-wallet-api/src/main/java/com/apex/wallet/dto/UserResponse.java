package com.apex.wallet.dto;

import com.apex.wallet.model.Role;
import com.apex.wallet.model.User;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String fullName,
        Role userRole,
        OffsetDateTime createdAt
) {
    public static UserResponse fromEntity(User user){
        return new UserResponse(user.getId(),user.getEmail(),user.getFullName() ,user.getUserRole(), user.getCreatedAt());
    }
}
