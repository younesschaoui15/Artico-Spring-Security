package com.chaoui.artico.service;

import com.chaoui.artico.dto.UserDTO;
import com.chaoui.artico.dto.request.*;
import com.chaoui.artico.dto.response.AdminDTOResponse;
import com.chaoui.artico.dto.response.ModeratorDTOResponse;
import com.chaoui.artico.entity.Credentials;
import com.chaoui.artico.entity.User;
import com.chaoui.artico.repository.AuthRepository;
import com.chaoui.artico.repository.RoleRepository;
import com.chaoui.artico.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       AuthRepository authRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminDTOResponse addAdmin(RegisterAdminDTORequest registerAdminDTORequest) {
        CredentialsDTORequest credentialsDTORequest = registerAdminDTORequest.credentialsDTO();
        AdminDTORequest adminDTORequest = registerAdminDTORequest.adminDTORequest();

        User savedUser = saveUserAndCredentials(adminDTORequest, credentialsDTORequest);
        AdminDTOResponse response = new AdminDTOResponse();
        response.mapFromEntity(savedUser);

        return response;
    }

    @Transactional
    public ModeratorDTOResponse addModerator(RegisterModeratorDTORequest newModeratorDTORequest) {
        CredentialsDTORequest credentialsDTORequest = newModeratorDTORequest.credentialsDTO();
        ModeratorDTORequest moderatorDTORequest = newModeratorDTORequest.moderatorDTORequest();

        User savedUser = saveUserAndCredentials(moderatorDTORequest, credentialsDTORequest);
        ModeratorDTOResponse response = new ModeratorDTOResponse();
        response.mapFromEntity(savedUser);

        return response;
    }

    private @NonNull User saveUserAndCredentials(UserDTO userDTORequest, CredentialsDTORequest credentialsDTORequest) {
        if (authRepository.findByUsername(credentialsDTORequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already taken: " + credentialsDTORequest.getUsername());
        } else {
            User user = userDTORequest.mapToEntity();

            // Save user
            User savedUser = userRepository.save(user);

            // Create credentials
            Credentials credentials = credentialsDTORequest.mapToEntity();
            credentials.setPasswordHash(passwordEncoder.encode(credentialsDTORequest.getPassword()));
            credentials.setUser(savedUser);
            credentials = authRepository.save(credentials);

            // Assign roles to user
            affectRolesToUser(new UserRolesDTO(savedUser.getId(), userDTORequest.getRoles()));

            return savedUser;
        }
    }

    public void affectRolesToUser(UserRolesDTO userRolesDTO) {
        userRepository.findById(userRolesDTO.userId())
                .ifPresentOrElse(user -> {
                    if (userRolesDTO.roleIds() != null && !userRolesDTO.roleIds().isEmpty()) {
                        userRolesDTO.roleIds().forEach(
                                roleId -> roleRepository.findById(roleId)
                                        .ifPresent(role -> user.getRoles().add(role))
                        );
                        userRepository.save(user);
                    } else {
                        throw new RuntimeException("Roles must not be empty!");
                    }
                }, () -> {
                    throw new RuntimeException("User not found!");
                });
    }
}
