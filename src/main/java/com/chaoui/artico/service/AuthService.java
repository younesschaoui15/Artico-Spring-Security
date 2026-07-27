package com.chaoui.artico.service;

import com.chaoui.artico.entity.Author;
import com.chaoui.artico.entity.Credentials;
import com.chaoui.artico.repository.AuthRepository;
import com.chaoui.artico.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Optional<Credentials> findCredentialsByUsername(String username) {
        return authRepository.findByUsername(username);
    }

    public Optional<Credentials> findCredentialsByUsernameAndPassword(String username, String password) {
        return authRepository.findByUsernameAndPassword(username, password);
    }

}
