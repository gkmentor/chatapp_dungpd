package com.example.chatapp_dungpd.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Message_Status")
public class MessageStatus {
    @EmbeddedId
    private MessageStatusId id;

    @ManyToOne
    @MapsId("messageId")
    @JoinColumn(name = "message_id")
    private Message message;

    @ManyToOne
    @MapsId("statusId")
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructor mặc định
    public MessageStatus() {
    }

    // Constructor khởi tạo
    public MessageStatus(Long messageId, Long statusId) {
        this.id = new MessageStatusId(messageId, statusId);
        this.updatedAt = LocalDateTime.now();
    }

    // Getters và Setters
    public MessageStatusId getId() {
        return id;
    }

    public void setId(MessageStatusId id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}


