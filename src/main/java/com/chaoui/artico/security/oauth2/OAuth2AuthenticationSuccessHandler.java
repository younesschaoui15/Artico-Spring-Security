package com.chaoui.artico.security.oauth2;

import com.chaoui.artico.dto.response.AuthenticationDTOResponse;
import com.chaoui.artico.entity.User;
import com.chaoui.artico.enums.UserStatus;
import com.chaoui.artico.security.CustomUserDetails;
import com.chaoui.artico.security.jwt.JwtService;
import com.chaoui.artico.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /*
     * Executed immediately after a successful Google login.
     * Generate your JWT tokens
     * Redirect back to the client with the response
     * */

    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public OAuth2AuthenticationSuccessHandler(JwtService jwtService,
                                              UserService userService,
                                              ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        User user = userService.findByEmail(oidcUser.getEmail()).orElseThrow();

        System.out.println("""
            # OAuth2 Authentication Success Event:
                Subject : %s
                Email : %s
                Claims : %s
                ID Token length: %s
                Access Token (To Google APIs): %s
            """.formatted(
            oidcUser.getSubject(),
            oidcUser.getEmail(),
            oidcUser.getClaims(),
            oidcUser.getIdToken().getTokenValue().length(),
            oidcUser.getAccessTokenHash()
        ));

        List<SimpleGrantedAuthority> authorities = user.getRoles()
            .stream().map((role) -> new SimpleGrantedAuthority(role.getName())).toList();

        UserDetails userDetails = new CustomUserDetails(
            oidcUser.getEmail(),
            null,
            authorities,
            LocalDateTime.now(),
            user.getStatus() == UserStatus.ACTIVE);

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        AuthenticationDTOResponse authResponse = new AuthenticationDTOResponse(accessToken, refreshToken);

        // Return the response to the client with the JWT tokens
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), authResponse);
    }
}
