package com.chaoui.artico.dto.request;

import com.chaoui.artico.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CredentialsDTO {

    private String username;
    private String password;

}
