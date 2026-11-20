package com.apex.wallet.dto;

import com.apex.wallet.model.WalletStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateWalletStatusRequest(
        @NotNull
        WalletStatus status
) {
}
