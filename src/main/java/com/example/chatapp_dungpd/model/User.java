package com.example.chatapp_dungpd.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import  lombok.*;
@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "User", indexes = {
        @Index(name = "idx_username", columnList = "username"),
        @Index(name = "idx_email", columnList = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, updatable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();

    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();

    }

}

