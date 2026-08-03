package com.chaoui.artico.service;

import com.chaoui.artico.dto.request.AuthenticationDTORequest;
import com.chaoui.artico.dto.response.AuthenticationDTOResponse;
import com.chaoui.artico.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UserDetailsService userDetailsService,
                       AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public AuthenticationDTOResponse authenticate(AuthenticationDTORequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new AuthenticationDTOResponse(accessToken, refreshToken);
    }

    public AuthenticationDTOResponse refreshToken(String refreshToken) {
        if (refreshToken == null || !jwtService.isRefreshToken(refreshToken))
            throw new AuthenticationCredentialsNotFoundException("Missing/invalid refresh token");

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, userDetails))
            throw new AuthenticationCredentialsNotFoundException("Refresh token expired");

        String accessToken = jwtService.generateAccessToken(userDetails);

        return new AuthenticationDTOResponse(accessToken, null);
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
