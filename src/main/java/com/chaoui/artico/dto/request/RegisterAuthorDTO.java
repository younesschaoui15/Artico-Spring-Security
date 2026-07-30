package com.chaoui.artico.dto.request;

import java.util.Set;

public record RegisterAuthorDTO(String username,
                                String password,
                                String firstName,
                                String lastName,
                                String email,
                                String nickname,
                                Set<Long> roleIds) {
}
