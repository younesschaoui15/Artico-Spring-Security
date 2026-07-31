package com.chaoui.artico.service;

import com.chaoui.artico.dto.request.LoginDTORequest;
import com.chaoui.artico.entity.Credentials;
import com.chaoui.artico.entity.User;
import com.chaoui.artico.repository.AuthRepository;
import com.chaoui.artico.repository.AuthorRepository;
import com.chaoui.artico.repository.ModeratorRepository;
import com.chaoui.artico.repository.UserRoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final AuthorRepository authorRepository;
    private final ModeratorRepository moderatorRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(AuthRepository authRepository,
                       AuthorRepository authorRepository,
                       ModeratorRepository moderatorRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.authRepository = authRepository;
        this.authorRepository = authorRepository;
        this.moderatorRepository = moderatorRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<String> login(LoginDTORequest cred) {
        User user = null;

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(cred.getUsername(), cred.getPassword())
            );
            user = authRepository.findByUsername(cred.getUsername()).get().getUser();
        } catch (AuthenticationException ex) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(cred.getEmail(), cred.getPassword())
            );
            user = authRepository.findByUsername(cred.getEmail()).get().getUser();
        }

        return ResponseEntity.ok("""
                Login successful!
                Welcome, %s
                """.formatted(user.getFirstName() + " " + user.getLastName()));
    }

    public Optional<Credentials> findCredentialsByUsername(String username) {
        return authRepository.findByUsername(username);
    }

    public Optional<Credentials> findCredentialsByUserEmailAndPassword(String username, String email) {
        return authRepository.findByUserEmail(username);
    }

//    @Transactional
//    public Credentials registerAuthor(AuthorDTORequest request) {
//        Author author = new Author();
//        author.setNickname(request.nickname());
//
//        User user = populateUser(
//                author,
//                request.firstName(),
//                request.lastName(),
//                request.email(),
//                request.roleIds());
//
//        return createCredentials(request.username(), request.password(), user);
//    }
//
//    @Transactional
//    public Credentials registerModerator(RegisterModeratorDTO request) {
//        Moderator moderator = new Moderator();
//        moderator.setPublicUsername(request.getPublicUsername());
//        User user = populateUser(
//                moderator,
//                request.getFirstName(),
//                request.getLastName(),
//                request.getEmail(),
//                request.getRoleIds());
//
//        return createCredentials(request.getUsername(), request.getPassword(), user);
//    }
//
//    private <T extends User> T populateUser(T user, String firstName, String lastName, String email, Set<Long> roleIds) {
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//        user.setEmail(email);
//        user.setRoles(resolveRoles(roleIds));
//
//        return user;
//    }
//
//    private Set<UserRole> resolveRoles(Set<Long> roleIds) {
//        if (roleIds == null || roleIds.isEmpty()) {
//            return new HashSet<>();
//        }
//        return new HashSet<>(userRoleRepository.findAllById(roleIds));
//    }
//
//    private Credentials createCredentials(String username, String password, User user) {
//        if (authRepository.findByUsername(username).isPresent()) {
//            throw new IllegalArgumentException("Username already taken: " + username);
//        } else {
//            if (user instanceof Author author)
//                user = authorRepository.save(author);
//            else if (user instanceof Moderator moderator) {
//                user = moderatorRepository.save(moderator);
//            }
//            System.out.println("# User saved: " + user.toString());
//        }
//
//        Credentials credentials = new Credentials();
//        credentials.setUsername(username);
//        credentials.setPasswordHash(passwordEncoder.encode(password));
//        credentials.setUser(user);
//
//        return authRepository.save(credentials);
//    }

}
