package com.example.chatapp_dungpd.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MessageStatusId implements Serializable {
    private Long messageId;
    private Long statusId;

    public MessageStatusId() {
    }

    public MessageStatusId(Long messageId, Long statusId) {
        this.messageId = messageId;
        this.statusId = statusId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

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



