package com.chaoui.artico.repository;

import com.chaoui.artico.entity.Author;
import com.chaoui.artico.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Credentials, Long> {

    Optional<Credentials> findByUsername(String username);
    Optional<Credentials> findByUsernameAndPassword(String username, String password);
}
