package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Message_Status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();

    }

    public MessageStatus(Long messageId, Long statusId) {
        this.id = new MessageStatusId(messageId, statusId);
        this.updatedAt = LocalDateTime.now();
    }
    public MessageStatus(Message message, Status status) {
        this.message = message;
        this.status = status;
        this.id = new MessageStatusId(message.getMessageId(), status.getStatusId());
        this.updatedAt = LocalDateTime.now();
    }
}


