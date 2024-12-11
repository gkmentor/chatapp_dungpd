package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;

import java.time.LocalDateTime;

@Entity
@Table(name = "Role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String roleName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();

    }
    public enum RoleName {
        ADMIN, OWNER, MEMBER
    }
}
