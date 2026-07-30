package com.chaoui.artico.dto.response;

import com.chaoui.artico.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserDTOResponse {
    protected Long id = null;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected UserStatus status;
    protected Set<Long> roles = new HashSet<>();
}