package com.chaoui.artico.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTORequest(@NotBlank String username,
                                       @NotBlank String password,
                                       @Nullable String email) {
}
