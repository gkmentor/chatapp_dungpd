package com.example.chatapp_dungpd.repository;

import com.example.chatapp_dungpd.model.GroupMember;
import com.example.chatapp_dungpd.model.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    Optional<GroupMember> findByChannelIdAndUserId(Long chatroomId, Long userId);

    @Query("SELECT COUNT(gm) FROM GroupMember gm WHERE gm.id.channelId = :chatroomId")
    int countMembersByChatroomId(@Param("chatroomId") Long chatroomId);
}
