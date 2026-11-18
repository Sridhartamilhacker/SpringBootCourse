package com.apex.wallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
    private UUID id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    public User(){

    }

    public User(String email, String fullName){
        this.id = UUID.randomUUID();
        this.email = email;
        this.fullName = fullName;
        this.createdAt = OffsetDateTime.now();
    }
}
