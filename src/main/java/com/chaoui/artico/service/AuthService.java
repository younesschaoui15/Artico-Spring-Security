package com.chaoui.artico.service;

import com.chaoui.artico.dto.request.RegisterAuthorDTO;
import com.chaoui.artico.dto.request.RegisterModeratorDTO;
import com.chaoui.artico.entity.*;
import com.chaoui.artico.repository.AuthRepository;
import com.chaoui.artico.repository.AuthorRepository;
import com.chaoui.artico.repository.ModeratorRepository;
import com.chaoui.artico.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthorRepository authorRepository;
    private final ModeratorRepository moderatorRepository;
    private final UserRoleRepository userRoleRepository;

    public AuthService(AuthRepository authRepository, AuthorRepository authorRepository,
                        ModeratorRepository moderatorRepository, UserRoleRepository userRoleRepository) {
        this.authRepository = authRepository;
        this.authorRepository = authorRepository;
        this.moderatorRepository = moderatorRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public Optional<Credentials> findCredentialsByUsername(String username) {
        return authRepository.findByUsername(username);
    }

    public Optional<Credentials> findCredentialsByUsernameAndPassword(String username, String password) {
        return authRepository.findByUsernameAndPassword(username, password);
    }

    @Transactional
    public Credentials registerAuthor(RegisterAuthorDTO request) {
        Author author = new Author();
        author.setNickname(request.getNickname());
        User user = populateUser(
                author,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getRoleIds());

        return createCredentials(request.getUsername(), request.getPassword(), user);
    }

    @Transactional
    public Credentials registerModerator(RegisterModeratorDTO request) {
        Moderator moderator = new Moderator();
        moderator.setPublicUsername(request.getPublicUsername());
        User user = populateUser(
                moderator,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getRoleIds());

        return createCredentials(request.getUsername(), request.getPassword(), user);
    }

    private <T extends User> T populateUser(T user, String firstName, String lastName, String email, Set<Long> roleIds) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRoles(resolveRoles(roleIds));

        return user;
    }

    private Set<UserRole> resolveRoles(Set<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(userRoleRepository.findAllById(roleIds));
    }

    public Credentials createCredentials(String username, String password, User user) {
        if (authRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken: " + username);
        } else {
            if (user instanceof Author author)
                user = authorRepository.save(author);
            else if (user instanceof Moderator moderator) {
                user = moderatorRepository.save(moderator);
            }
            System.out.println("# User saved: " + user.toString());
        }

        Credentials credentials = new Credentials();
        credentials.setUsername(username);
        credentials.setPassword(password);
        credentials.setUser(user);

        return authRepository.save(credentials);
    }

}
