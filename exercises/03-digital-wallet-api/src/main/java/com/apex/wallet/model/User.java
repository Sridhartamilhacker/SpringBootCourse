package com.apex.wallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(name="users")
@Entity
public class User {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(name = "role", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Role userRole = Role.ROLE_USER;

    public User(){

    }

    public User(String email, String fullName, String passwordHash){
        this.email = email;
        this.fullName = fullName;
        this.createdAt = OffsetDateTime.now();
        this.passwordHash = passwordHash;
    }
}
