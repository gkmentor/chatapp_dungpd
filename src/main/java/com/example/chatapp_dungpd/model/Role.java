package com.example.chatapp_dungpd.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false)
    private String roleName;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Role(Long defaultRoleId) {
    }

    public Role() {

    }

    // Getters and Setters

    public Long getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
