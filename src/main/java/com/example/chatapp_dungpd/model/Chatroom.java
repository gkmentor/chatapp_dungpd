package com.example.chatapp_dungpd.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Chatroom")
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatroomId;

    private String channelName;

    private Boolean isDirectedChat = false;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    public void setCreatedUserId(Long userId) {
    }

    public void setChannelName(String channelName) {
    }

    public void setIsDirectedChat(boolean b) {
    }

    public Long getChatroomId() {
        return chatroomId;
    }

    // Getters and Setters
}
