package com.chaoui.artico.controller;

import com.chaoui.artico.dto.request.AuthenticationDTORequest;
import com.chaoui.artico.dto.request.RefreshTokenDTORequest;
import com.chaoui.artico.dto.response.AuthenticationDTOResponse;
import com.chaoui.artico.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping(value = "${app.request-mapping.auth}")
public class AuthController {

    private final AuthService authService;

    @Value("${app.request-mapping.auth}")
    private String authRequestMapping;
    @Value("${security.jwt.refresh-token-expiration}")
    private Duration refreshTokenExpiration;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTOResponse> login(@Valid @RequestBody AuthenticationDTORequest request,
                                                           HttpServletResponse response) {
        AuthenticationDTOResponse authResponse = authService.authenticate(request);
        // set the refresh token cookie in the response header
        setRefreshTokenCookie(
            authResponse.refreshToken(),
            refreshTokenExpiration,
            response);

        return ResponseEntity.ok(authResponse);
    }

    //Using a Cookie value (not in the request body)
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationDTOResponse> refreshToken(@CookieValue(name = "refreshTokenQ", required = false) String refreshToken) {
        System.out.println("# refreshToken: "+refreshToken);
        AuthenticationDTOResponse response = authService.refreshToken(refreshToken);
        System.out.println("# response: "+ response);

        return ResponseEntity.ok(response);
    }

    //Using a Request Body
    @PostMapping("/refresh-token-2")
    public ResponseEntity<AuthenticationDTOResponse> refreshToken(@Valid @RequestBody RefreshTokenDTORequest request) {
        AuthenticationDTOResponse response = authService.refreshToken(request.refreshToken());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        setRefreshTokenCookie(null, Duration.ZERO, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // to set the refresh token cookie in the response header (not in the response body)
    private void setRefreshTokenCookie(String token, Duration maxAge, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie
            .from("refreshToken", token)
            .httpOnly(true) // JS cannot read it (mitigates XSS theft)
//            .secure(true) // sent only over HTTPS
            .sameSite("Strict") // blocks cross-site attachment (CSRF mitigation)
            .path(authRequestMapping) // only sent to auth endpoints, not every request
            .maxAge(maxAge)
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
