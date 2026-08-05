package com.chaoui.artico.dto.response;

import com.chaoui.artico.dto.ModeratorDTO;
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
public class ModeratorDTOResponse extends ModeratorDTO {

    private Long id;

    @Override
    public void mapFromEntity(User entity) {
        Moderator moderator = (Moderator) entity;
        super.mapFromEntity(moderator);

        id = moderator.getId();
    }

    @Override
    public Moderator mapToEntity() {
        Moderator moderator = super.mapToEntity();

        if (id != null)
            moderator.setId(id);

        return moderator;
    }
}