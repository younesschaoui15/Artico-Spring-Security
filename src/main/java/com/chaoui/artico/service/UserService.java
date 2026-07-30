package com.chaoui.artico.service;

import com.chaoui.artico.dto.request.CredentialsDTO;
import com.chaoui.artico.dto.request.ModeratorDTORequest;
import com.chaoui.artico.dto.request.UserRolesDTO;
import com.chaoui.artico.dto.response.ModeratorDTOResponse;
import com.chaoui.artico.entity.Credentials;
import com.chaoui.artico.entity.Moderator;
import com.chaoui.artico.entity.User;
import com.chaoui.artico.repository.AuthRepository;
import com.chaoui.artico.repository.RoleRepository;
import com.chaoui.artico.repository.UserRepository;
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

    @Transactional
    public ModeratorDTOResponse addModerator(ModeratorDTORequest moderatorDTO, CredentialsDTO credentialsDTO) {
        if (authRepository.findByUsername(credentialsDTO.username()).isPresent()) {
            throw new IllegalArgumentException("Username already taken: " + credentialsDTO.username());
        } else {
            Moderator moderator = new Moderator();
            moderator.setFirstName(moderatorDTO.firstName());
            moderator.setLastName(moderatorDTO.lastName());
            moderator.setEmail(moderatorDTO.email());
            moderator.setPublicUsername(moderatorDTO.publicUsername());
            moderator.setStatus(moderatorDTO.status());
            moderator.setPublicUsername(moderatorDTO.publicUsername());
            moderator.setVisible(moderatorDTO.visible());

            // Save moderator
            Moderator savedModerator = userRepository.save(moderator);

            // Create credentials
            createCredentials(credentialsDTO.username(), credentialsDTO.password(), savedModerator);

            // Assign roles to user
            affectRolesToUser(new UserRolesDTO(savedModerator.getId(), moderatorDTO.roles()));

            // Create response
            ModeratorDTOResponse response = new ModeratorDTOResponse(moderatorDTO);
            response.setId(savedModerator.getId());

            return response;
        }
    }

    private Credentials createCredentials(String username, String password, User user) {
        Credentials credentials = new Credentials();
        credentials.setUsername(username);
        credentials.setPassword(passwordEncoder.encode(password));
        credentials.setUser(user);

        return authRepository.save(credentials);
    }
}
