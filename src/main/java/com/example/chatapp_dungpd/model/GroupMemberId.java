package com.example.chatapp_dungpd.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GroupMemberId implements Serializable {
    private Long channelId;
    private Long userId;

    // Default constructor, equals, and hashCode
    public GroupMemberId() {}

    public GroupMemberId(Long channelId, Long userId) {
        this.channelId = channelId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupMemberId)) return false;
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(channelId, that.channelId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, userId);
    }
}
