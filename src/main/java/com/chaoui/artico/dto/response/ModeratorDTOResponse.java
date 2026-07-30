package com.chaoui.artico.dto.response;

import com.chaoui.artico.dto.request.ModeratorDTORequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ModeratorDTOResponse extends UserDTOResponse {
    private String publicUsername;
    private Boolean visible;

    public ModeratorDTOResponse(ModeratorDTORequest request) {
        this.firstName = request.firstName();
        this.lastName = request.lastName();
        this.email = request.email();
        this.status = request.status();
        this.publicUsername = request.publicUsername();
        this.visible = request.visible();
        this.roles = request.roles();
    }
}