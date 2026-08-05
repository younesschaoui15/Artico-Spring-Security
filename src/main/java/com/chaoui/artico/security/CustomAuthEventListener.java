package com.chaoui.artico.security;


import com.chaoui.artico.repository.AuthRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

        // Update the user's last login date
//        authRepository.findByUsername(username)
//            .or(() -> authRepository.findByUserEmail(username))
//            .ifPresent(credentials -> {
//                credentials.setLastLogin(LocalDateTime.now());
//                authRepository.save(credentials);
//            });

        System.out.println("""
            # Custom Auth Event Listener (Authentication Success):
                Subject : %s
                Authenticated : %s
                Principal : %s
            """.formatted(
            authentication.getName(),
            authentication.isAuthenticated(),
            authentication.getPrincipal()));
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent failureEvent) {
        var username = failureEvent.getAuthentication().getName();
        var authenticated = failureEvent.getAuthentication().isAuthenticated();

        System.out.println("""
            # Custom Auth Event Listener (Authentication Failure):
                Subject : %s
                Authenticated : %s
            """.formatted(username, authenticated));
    }
}
