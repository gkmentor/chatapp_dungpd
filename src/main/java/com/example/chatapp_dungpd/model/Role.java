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

    @Column(nullable = false)
    private String roleName;

    private LocalDateTime createdAt = LocalDateTime.now();
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();

    }

}
