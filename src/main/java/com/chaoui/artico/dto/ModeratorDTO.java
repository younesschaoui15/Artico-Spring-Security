package com.chaoui.artico.dto;

import com.chaoui.artico.entity.Moderator;
import com.chaoui.artico.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract non-sealed class ModeratorDTO extends UserDTO {

    private String publicUsername;
    private Boolean visible = true;

    @Override
    public void mapFromEntity(User entity) {
        Moderator moderator = (Moderator) entity;
        super.mapFromEntity(moderator);

        publicUsername = moderator.getPublicUsername();
        visible = moderator.isVisible();
    }

    @Override
    public Moderator mapToEntity() {
        Moderator moderator = new Moderator();
        entityMapping(moderator);

        moderator.setPublicUsername(publicUsername);
        moderator.setVisible(visible);

        return moderator;
    }
}