package com.chaoui.artico.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterModeratorDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String publicUsername;
    private Set<Long> roleIds;

}
