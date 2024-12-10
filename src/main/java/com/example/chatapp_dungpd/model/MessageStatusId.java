package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;



@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageStatusId implements Serializable {
    private Long messageId;
    private Long statusId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageStatusId)) return false;
        MessageStatusId that = (MessageStatusId) o;
        return Objects.equals(messageId, that.messageId) &&
                Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, statusId);
    }
}



