package com.chaoui.artico.controller;

import com.chaoui.artico.dto.request.CredentialsDTO;
import com.chaoui.artico.entity.Credentials;
import com.chaoui.artico.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CredentialsDTO cred) {
        Optional<Credentials> credentials = authService.findCredentialsByUsernameAndPassword(cred.getUsername(), cred.getPassword());

        if (credentials.isPresent()) {
            System.out.println("# Credentials found:  " + credentials.get().toString());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
