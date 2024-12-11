package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Chatroom", indexes = {
        @Index(name = "idx_channel_name", columnList = "channelName"),
        @Index(name = "idx_created_user", columnList = "createdUserId")
})
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatroomId;

    @Column(nullable = false)
    private Long createdUserId;

    @Column(nullable = false, unique = true, length = 100)
    private String channelName;

    @Column(nullable = false)
    private Boolean isDirectedChat = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();

    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();

    }

    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

}
