package com.chaoui.artico.dto.request;

public record NewModeratorDTO(
        ModeratorDTORequest moderatorDTORequest,
        CredentialsDTO credentialsDTO
) {
}
