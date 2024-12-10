package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Group_Member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class GroupMember {

    @EmbeddedId
    private GroupMemberId id;

    @ManyToOne
    @MapsId("channelId") // Links with channelId in GroupMemberId
    @JoinColumn(name = "channel_id", nullable = false)
    private Chatroom chatroom;

    @ManyToOne
    @MapsId("userId") // Links with userId in GroupMemberId
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();


    public GroupMember(Chatroom chatroom, User user, Role role) {
        this.id = new GroupMemberId(chatroom.getChatroomId(), user.getUserId());
        this.chatroom = chatroom;
        this.user = user;
        this.role = role;
        this.joinedAt = LocalDateTime.now();
    }

}


