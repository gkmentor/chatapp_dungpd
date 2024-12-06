package com.example.chatapp_dungpd.repository;

import com.example.chatapp_dungpd.model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    @Query("SELECT c FROM Chatroom c WHERE c.isDirectedChat = TRUE AND " +
            "EXISTS (SELECT gm FROM GroupMember gm WHERE gm.id.channelId = c.chatroomId AND gm.id.userId = :userId1) AND " +
            "EXISTS (SELECT gm FROM GroupMember gm WHERE gm.id.channelId = c.chatroomId AND gm.id.userId = :userId2)")

    List<Chatroom> findDirectedChatroomsByUserIds(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
}
