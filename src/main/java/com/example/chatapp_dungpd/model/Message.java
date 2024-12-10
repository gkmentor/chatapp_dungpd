package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private Chatroom chatroom;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();

    }

//    Message message = Message.builder()
//            .chatroom(chatroom)
//            .sender(user)
//            .content("Hello World!")
//            .build();
}
