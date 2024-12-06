package com.example.chatapp_dungpd.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Group_Member")
public class GroupMember {
    @EmbeddedId
    private GroupMemberId id;

    @ManyToOne
    @MapsId("channelId") // Liên kết với channelId trong GroupMemberId
    @JoinColumn(name = "channel_id")
    private Chatroom chatroom;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Role role;

    private LocalDateTime joinedAt = LocalDateTime.now();

    public GroupMember(Long chatroomId, Long userId, Long defaultRoleId) {
        this.id = new GroupMemberId(chatroomId, user.getUserId());
        this.user = user;  // Gán đối tượng User
        this.role = role;  // Gán đối tượng Role
        this.joinedAt = LocalDateTime.now();
    }

    public GroupMember() {

    }

    // Getters and Setters

    public Role getRole() {
        return role;
    }
}


