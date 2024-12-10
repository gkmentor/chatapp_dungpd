package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Chatroom")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatroomId;

    private String channelName;
    private Boolean isDirectedChat = false;

    private LocalDateTime createdAt;
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
