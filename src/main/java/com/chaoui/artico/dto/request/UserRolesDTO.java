package com.chaoui.artico.dto.request;

import java.util.Set;

public record UserRolesDTO(Long userId, Set<Long> roleIds) {
}
