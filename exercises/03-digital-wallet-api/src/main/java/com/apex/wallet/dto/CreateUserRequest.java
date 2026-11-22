package com.apex.wallet.dto;

import com.apex.wallet.model.Role;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank
        String email,
        @NotBlank
        String fullName,
        @NotBlank
        String passwordHash,
        @Nullable
        Role userRole) {
}
