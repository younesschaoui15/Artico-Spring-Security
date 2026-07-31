package com.chaoui.artico.security;


import com.chaoui.artico.repository.AuthRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class CustomAuthEventListener {

    private final AuthRepository authRepository;

    public CustomAuthEventListener(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @EventListener
    @Transactional
    public void onAuthenticationSuccess(AuthenticationSuccessEvent successEvent) {
        var authentication = successEvent.getAuthentication();
        String username = authentication.getName();

        // Update the user's last login date
        authRepository.findByUsername(username)
                .or(() -> authRepository.findByUserEmail(username))
                .ifPresent(credentials -> {
                    credentials.setLastLogin(LocalDateTime.now());
                    authRepository.save(credentials);
                });

        System.out.println("""
                # Authentication Success Event:
                    Username : %s
                    Authenticated : %s
                    Authorities : %s
                    Last login : %s
                """.formatted(username,
                authentication.isAuthenticated(),
                authentication.getAuthorities(),
                LocalDateTime.now()));
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent failureEvent) {
        var username = failureEvent.getAuthentication().getName();
        var authenticated = failureEvent.getAuthentication().isAuthenticated();

        System.out.println("""
                # Authentication Failure Event:
                    username : %s
                    authenticated : %s
                """.formatted(username, authenticated));
    }
}
