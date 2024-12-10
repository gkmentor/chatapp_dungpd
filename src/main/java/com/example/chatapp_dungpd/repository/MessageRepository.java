package com.example.chatapp_dungpd.repository;

import com.example.chatapp_dungpd.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatroomIdAndCreatedAtBetween(Long chatroomId, LocalDateTime from, LocalDateTime to);

    // Custom query to find messages by chatroomId
    @Query("SELECT m FROM Message m WHERE m.chatroom.chatroomId = :chatroomId")
    List<Message> findByChatroomId(@Param("chatroomId") Long chatroomId);

    Object findUserById(Long senderId);
}
