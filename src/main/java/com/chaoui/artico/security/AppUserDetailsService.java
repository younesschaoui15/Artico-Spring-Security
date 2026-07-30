package com.chaoui.artico.security;

import com.chaoui.artico.entity.Credentials;
import com.chaoui.artico.enums.UserStatus;
import com.chaoui.artico.repository.AuthRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    public AppUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Credentials credentials = authRepository.findByUsername(username).orElseGet(
                () -> authRepository.findByUserEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User does not exist with username/email: " + username))
        );

        // Get user roles from the database
        List<SimpleGrantedAuthority> authorities = credentials.getUser().getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        var isEnabled = credentials.getUser().getStatus() == UserStatus.ACTIVE;

        UserDetails userDetails = new AppUserDetails(
                credentials.getUsername(),
                credentials.getPassword(),
                authorities,
                isEnabled
        );

        System.out.println("""
        # User Loaded Successfully
            # Username : %s
            # Authorities : %s
        """.formatted(userDetails.getUsername(), authorities));

        return userDetails;
    }
}
