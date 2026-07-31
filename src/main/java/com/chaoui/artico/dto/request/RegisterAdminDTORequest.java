package com.chaoui.artico.dto.request;

public record RegisterAdminDTORequest(
        AdminDTORequest adminDTORequest,
        CredentialsDTORequest credentialsDTO
) {
}
