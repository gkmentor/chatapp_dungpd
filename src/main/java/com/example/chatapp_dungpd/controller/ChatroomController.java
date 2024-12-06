package com.example.chatapp_dungpd.controller;

import com.example.chatapp_dungpd.model.Chatroom;
import com.example.chatapp_dungpd.service.ChatroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatroomController {

    @Autowired
    private ChatroomService chatroomService;

    @PostMapping
    public ResponseEntity<Chatroom> createChatroom(@RequestBody Long userId,  @RequestBody String channelName) {
        Chatroom newChatroom = chatroomService.createChatroom(userId, channelName);
        return ResponseEntity.ok(newChatroom);
    }

    @GetMapping
    public ResponseEntity<List<Chatroom>> getAllChatrooms() {
        return ResponseEntity.ok(chatroomService.getAllChatrooms());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatroom(@PathVariable Long user_id, @PathVariable Long chatroom_id) {
        try {
            chatroomService.deleteChatroom(chatroom_id, user_id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.noContent().build();
    }
}

