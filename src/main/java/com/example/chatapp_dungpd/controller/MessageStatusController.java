package com.example.chatapp_dungpd.controller;

import com.example.chatapp_dungpd.dto.MessageStatusUpdateRequest;
import com.example.chatapp_dungpd.model.MessageStatus;

import com.example.chatapp_dungpd.service.MessageStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/message-status")
public class MessageStatusController {


    private final MessageStatusService messageStatusService;

    public MessageStatusController(MessageStatusService messageStatusService) {
        this.messageStatusService = messageStatusService;
    }

    @PostMapping
    public ResponseEntity<MessageStatus> updateMessageStatus(@RequestBody MessageStatusUpdateRequest request) {
        MessageStatus updatedStatus = messageStatusService.updateMessageStatus(request);
        return ResponseEntity.ok(updatedStatus);
    }

    @DeleteMapping("/{messageId}/{statusId}")
    public ResponseEntity<Void> removeMessageStatus(@PathVariable Long messageId, @PathVariable Long statusId) {
        messageStatusService.removeMessageStatus(messageId, statusId);
        return ResponseEntity.noContent().build();
    }
}
