package com.example.chatapp_dungpd.controller;

import com.example.chatapp_dungpd.dto.ChatroomRequest;
import com.example.chatapp_dungpd.exception.ResourceNotFoundException;
import com.example.chatapp_dungpd.model.Chatroom;
import com.example.chatapp_dungpd.service.ChatroomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chatrooms")
public class ChatroomController {


    private final ChatroomService chatroomService;

    public ChatroomController(ChatroomService chatroomService) {
        this.chatroomService = chatroomService;
    }

    @PostMapping
    public ResponseEntity<Chatroom> createChatroom(@RequestBody ChatroomRequest chatroomRequest) {
        Chatroom newChatroom = chatroomService.createChatroom(chatroomRequest.getUserId(), chatroomRequest.getChannelName());
        return ResponseEntity.ok(newChatroom);
    }

    @GetMapping
    public ResponseEntity<List<Chatroom>> getAllChatrooms() {
        return ResponseEntity.ok(chatroomService.getAllChatrooms());
    }

    @DeleteMapping("/{user_id}/{chatroom_id}")
    public ResponseEntity<Void> deleteChatroom(@PathVariable Long user_id, @PathVariable Long chatroom_id) {
        try {
            chatroomService.deleteChatroom(chatroom_id, user_id);
            return ResponseEntity.noContent().build();
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have access to this chatroom", e);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chatroom or User not found", e);
        }
    }
}

