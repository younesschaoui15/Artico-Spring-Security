package com.chaoui.artico.security;

import com.chaoui.artico.security.jwt.CustomJwtAuthenticationFilter;
import com.chaoui.artico.security.jwt.JwtProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableConfigurationProperties({JwtProperties.class}) // to register JwtProperties as a bean
public class SecurityConfig {

    @Value("${app.request-mapping.auth}")
    private String authRequestMapping;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CustomAuthEntryPoint customAuthEntryPoint) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", authRequestMapping+"/**").permitAll()
                .requestMatchers("/actuator/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(b -> b.authenticationEntryPoint(customAuthEntryPoint))
//            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(
                customJwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
