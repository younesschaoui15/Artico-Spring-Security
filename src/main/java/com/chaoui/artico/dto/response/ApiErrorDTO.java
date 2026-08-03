package com.chaoui.artico.dto.response;

import java.time.Instant;

public record ApiErrorDTO(Instant timestamp,
                          int status,
                          String message) {
}
