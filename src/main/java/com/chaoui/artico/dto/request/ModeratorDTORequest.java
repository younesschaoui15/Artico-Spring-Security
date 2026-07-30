package com.chaoui.artico.dto.request;

import com.chaoui.artico.enums.UserStatus;

import java.util.HashSet;
import java.util.Set;

public record ModeratorDTORequest(
        String firstName,
        String lastName,
        String email,
        UserStatus status,
        String publicUsername,
        Boolean visible,
        Set<Long> roles) {

    public ModeratorDTORequest {
        if (status == null) status = UserStatus.ACTIVE;
        if (visible == null) visible = true;
        if (roles == null) roles = new HashSet<>();
    }
}