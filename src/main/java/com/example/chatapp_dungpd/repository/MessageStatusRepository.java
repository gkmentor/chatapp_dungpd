package com.example.chatapp_dungpd.repository;

import com.example.chatapp_dungpd.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, MessageStatusId> {
    // Custom methods if needed
}
