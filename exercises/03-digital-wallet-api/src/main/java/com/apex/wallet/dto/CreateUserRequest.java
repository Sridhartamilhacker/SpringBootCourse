package com.apex.wallet.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank
        String email,
        @NotBlank
        String fullName) {
}
