package com.example.chatapp_dungpd.controller;

import com.example.chatapp_dungpd.model.Message;
import com.example.chatapp_dungpd.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {


    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message newMessage = messageService.createMessage(message);
        return ResponseEntity.ok(newMessage);
    }

    @GetMapping("/chatroom/{chatroomId}")
    public ResponseEntity<List<Message>> getMessagesByChatroomId(@PathVariable Long chatroomId) {
        List<Message> messages = messageService.getMessagesByChatroomId(chatroomId);
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
