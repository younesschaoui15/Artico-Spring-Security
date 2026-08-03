package com.chaoui.artico.security.jwt;

import com.chaoui.artico.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public CustomJwtAuthenticationFilter(JwtService jwtService,
                                         CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (!jwtService.isHeaderFormatValid(authHeader)) {
            // continue with the request chain if it's not a login request
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext securityContext = SecurityContextHolder.getContext();
        String jwtToken = authHeader.substring(7);
        String username = jwtService.extractUsername(jwtToken);

        // if the token is valid (username exists), configure Spring Security to manually set the authentication
        if (username != null && securityContext.getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // check if the token is valid (not expired, not revoked), then set the authentication
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                // create a new authentication token with the user details, then set it in the security context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );

                // set the authentication details (e.g., IP address) in the token
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // set the authentication in the security context
                securityContext.setAuthentication(authentication);
            }
        }

        // continue with the request chain
        filterChain.doFilter(request, response);
    }
}
