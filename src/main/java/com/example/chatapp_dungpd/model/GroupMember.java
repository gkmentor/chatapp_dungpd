package com.example.chatapp_dungpd.model;
import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Group_member", indexes = {
        @Index(name = "idx_channel_user", columnList = "channelId, userId")
})



public class GroupMember {
    @Id
    private Long channelId;

    @Id
    private Long userId;

    @Column(nullable = false)
    private Long roleId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();

}
// Class đại diện cho composite key


