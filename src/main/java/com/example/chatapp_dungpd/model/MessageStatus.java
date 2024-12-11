package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Message_status", indexes = {
        @Index(name = "idx_message_id", columnList = "messageId"),
        @Index(name = "idx_status_id", columnList = "statusId")
})

public class MessageStatus {
    @Id
    @Column(nullable = false)
    private Long messageId;

    @Id
    @Column(nullable = false)
    private Long statusId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


