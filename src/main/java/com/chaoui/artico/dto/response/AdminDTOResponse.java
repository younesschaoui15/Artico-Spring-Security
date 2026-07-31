package com.chaoui.artico.dto.response;

import com.chaoui.artico.dto.AdminDTO;
import com.chaoui.artico.entity.Admin;
import com.chaoui.artico.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AdminDTOResponse extends AdminDTO {

    private Long id;

    @Override
    public void mapFromEntity(User entity) {
        Admin admin = (Admin) entity;
        super.mapFromEntity(admin);

        id = admin.getId();
    }

    @Override
    public Admin mapToEntity() {
        Admin admin = super.mapToEntity();

        if (id != null)
            admin.setId(id);

        return admin;
    }
}