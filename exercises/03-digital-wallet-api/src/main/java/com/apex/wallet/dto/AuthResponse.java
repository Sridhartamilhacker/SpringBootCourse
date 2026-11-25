package com.apex.wallet.dto;

import com.apex.wallet.model.Role;

public record AuthResponse(
        String token,
        String email,
        String role
) {
}
