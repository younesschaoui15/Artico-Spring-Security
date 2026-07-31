package com.chaoui.artico.dto;

import com.chaoui.artico.entity.Admin;
import com.chaoui.artico.entity.User;
import com.chaoui.artico.enums.AdminLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract non-sealed class AdminDTO extends UserDTO {

    private AdminLevel level = AdminLevel.BASIC;
    private Long createdBy;

    @Override
    public void mapFromEntity(User entity) {
        Admin admin = (Admin) entity;
        super.mapFromEntity(admin);

        level = admin.getLevel();
        createdBy = admin.getCreatedBy();
    }

    @Override
    public Admin mapToEntity() {
        Admin admin = new Admin();
        entityMapping(admin);

        admin.setLevel(level);
        admin.setCreatedBy(createdBy);

        return admin;
    }
}