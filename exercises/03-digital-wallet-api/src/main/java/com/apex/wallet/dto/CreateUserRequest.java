package com.apex.wallet.dto;

import com.apex.wallet.model.Role;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank
        String email,
        @NotBlank
        String fullName,
        @NotBlank
        String password,
        Role role
        ) {
}
