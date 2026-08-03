package com.chaoui.artico.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDTORequest(@NotBlank String refreshToken) {
}
