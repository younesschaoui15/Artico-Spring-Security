package com.chaoui.artico.security.oauth2;

import com.chaoui.artico.entity.User;
import com.chaoui.artico.service.UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {
    /*
     * Responsible for getting user information from Google.
     * Validate OIDC user
     * Load Google profile
     * Synchronize local user (create new user if needed)
     * Output: User object (OidcUser)
     * */

    private final UserService userService;

    public CustomOidcUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);

        //IMPORTANT: We won't create credentials here, because we are using Google OAuth2
        User user = userService.findByEmail(oidcUser.getEmail())
            .orElseGet(() -> userService.createNewUser(oidcUser));

        System.out.println("""
            # OIDC User loaded successfully:
                Email: %s
                User Entity: %s
            """.formatted(oidcUser.getEmail(), user));

        return oidcUser;
    }
}