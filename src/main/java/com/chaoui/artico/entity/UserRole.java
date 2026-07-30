package com.chaoui.artico.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data @AllArgsConstructor @NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @PrePersist
    @PreUpdate
    private void normalizeName() {
        if (name != null && !name.toUpperCase().startsWith("ROLE_")) {
            name = "ROLE_" + name.toUpperCase();
        }
    }

}
