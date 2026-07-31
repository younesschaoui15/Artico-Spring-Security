package com.chaoui.artico.dto.request;

public record RegisterModeratorDTORequest(
        ModeratorDTORequest moderatorDTORequest,
        CredentialsDTORequest credentialsDTO
) {
}
