package com.chaoui.artico.dto.request;

import com.chaoui.artico.entity.UserRole;
import com.chaoui.artico.enums.UserStatus;

import java.util.HashSet;
import java.util.Set;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        UserStatus status,
        Set<UserRole> roles) {
    public UserDTO {
        if (status == null)
            status = UserStatus.ACTIVE;
        if (roles == null)
            roles = new HashSet<>();
    }
}
