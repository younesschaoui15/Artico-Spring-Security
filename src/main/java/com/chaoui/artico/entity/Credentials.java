package com.chaoui.artico.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String passwordHash;
    @JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime passwordChangedAt;
    @JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime lastLogin;
    @JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime updatedAt;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
