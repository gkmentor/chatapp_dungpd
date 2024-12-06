package com.example.chatapp_dungpd.controller;

import com.example.chatapp_dungpd.model.MessageStatus;
import com.example.chatapp_dungpd.model.MessageStatusId;
import com.example.chatapp_dungpd.repository.MessageStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message-status")
public class MessageStatusController {

    @Autowired
    private MessageStatusRepository messageStatusRepository;

    @PostMapping
    public ResponseEntity<MessageStatus> updateMessageStatus(@RequestBody MessageStatus messageStatus) {
        MessageStatus updatedStatus = messageStatusRepository.save(messageStatus);
        return ResponseEntity.ok(updatedStatus);
    }

    @DeleteMapping("/{messageId}/{statusId}")
    public ResponseEntity<Void> removeMessageStatus(@PathVariable Long messageId, @PathVariable Long statusId) {
        MessageStatusId id = new MessageStatusId(messageId, statusId);
        messageStatusRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
