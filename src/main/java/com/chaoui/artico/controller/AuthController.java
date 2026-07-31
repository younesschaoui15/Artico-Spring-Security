package com.chaoui.artico.controller;

import com.chaoui.artico.dto.request.LoginDTORequest;
import com.chaoui.artico.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTORequest cred) {
        return authService.login(cred);

//        //Find credentials by username and password
//        Optional<Credentials> credentials = authService.findCredentialsByUsername(cred.username(), passwordEncoder.encode(cred.password()));
//
//        //If not found, find by email and password
//        if (credentials.isEmpty())
//            credentials = authService.findCredentialsByUserEmailAndPassword(cred.email(), passwordEncoder.encode(cred.password()));
//
//        if (credentials.isPresent()) {
//            System.out.println("# Credentials found:  " + credentials.get());
//            return new ResponseEntity<>("Login OK", HttpStatus.OK);
//        } else
//            return new ResponseEntity<>("Login fails, user does not exist!", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
