package com.chaoui.artico.dto.request;

import com.chaoui.artico.dto.abstraction.EntityMappable;
import com.chaoui.artico.entity.Credentials;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialsDTORequest implements EntityMappable<Credentials> {

    private String username;
    private String password;


    @Override
    public void mapFromEntity(Credentials entity) {
        username = entity.getUsername();
        password = entity.getPasswordHash();
    }

    @Override
    public Credentials mapToEntity() {
        Credentials credentials = new Credentials();
        credentials.setUsername(username);

        return credentials;
    }
}
