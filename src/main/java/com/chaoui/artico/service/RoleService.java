package com.chaoui.artico.service;

import com.chaoui.artico.entity.UserRole;
import com.chaoui.artico.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserRole addRole(String role) {
        return this.roleRepository.save(new UserRole(null, role));
    }

    public Long removeRole(Long id) {
        roleRepository.findById(id).ifPresentOrElse((role) -> {
            roleRepository.delete(role);
        }, () -> {
            throw new EntityNotFoundException("Role not found with id " + id);
        });

        return id;
    }
}
