package com.chaoui.artico.dto;

import com.chaoui.artico.dto.abstraction.EntityMappable;
import com.chaoui.artico.entity.User;
import com.chaoui.artico.entity.UserRole;
import com.chaoui.artico.enums.UserStatus;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public sealed abstract class UserDTO
        implements EntityMappable<User>
        permits AdminDTO, AuthorDTO, ModeratorDTO {

    private String firstName;
    private String lastName;
    private String email;
    private UserStatus status = UserStatus.ACTIVE;
    private Set<Long> roles = new HashSet<>();

    @Override
    public void mapFromEntity(User entity) {
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        status = entity.getStatus();
        roles = entity.getRoles().stream().map(UserRole::getId).collect(Collectors.toSet());
    }

    public User entityMapping(User user) {
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setStatus(this.status);

        return user;
    }

}
