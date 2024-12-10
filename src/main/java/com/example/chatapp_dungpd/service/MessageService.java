package com.example.chatapp_dungpd.service;

import com.example.chatapp_dungpd.model.*;
import com.example.chatapp_dungpd.repository.*;
import com.example.chatapp_dungpd.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageStatusRepository messageStatusRepository;
    private final StatusRepository statusRepository;
    private final ChatroomRepository chatroomRepository;
    private final UserRepository userRepository;

    // Constructor
    public MessageService(MessageRepository messageRepository,
                          MessageStatusRepository messageStatusRepository,
                          StatusRepository statusRepository,
                          ChatroomRepository chatroomRepository,
                          UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.messageStatusRepository = messageStatusRepository;
        this.statusRepository = statusRepository;
        this.chatroomRepository = chatroomRepository;
        this.userRepository = userRepository;
    }

    // Gửi tin nhắn
    public Message sendMessage(Long chatroomId, Long senderId, String content) {
        Message message = new Message();

        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                        .orElseThrow(() -> new ResourceNotFoundException("Chatroom not found with id" +chatroomId));
        message.setChatroom(chatroom);

        User sender  = userRepository.findById(senderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Chatroom not found with id" +senderId));
        message.setSender(sender);

        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    //to create a new message
    public Message createMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(message);  // Save message to DB and return
    }

    // get messages by chatroomId
    public List<Message> getMessagesByChatroomId(Long chatroomId) {
        return messageRepository.findByChatroomId(chatroomId);  // Assuming you have a custom query in the repository
    }

    // delete a message by ID
    public void deleteMessage(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);  // Delete message by ID
        } else {
            throw new ResourceNotFoundException("Message not found with id: " + id);
        }
    }

    // Lấy tin nhắn theo khoảng thời gian
    public List<Message> getMessagesByChatroomAndDate(Long chatroomId, LocalDateTime from, LocalDateTime to) {
        return messageRepository.findByChatroomIdAndCreatedAtBetween(chatroomId, from, to);
    }

    // Cập nhật trạng thái tin nhắn đã đọc, chưa đọc, v.v.
    public MessageStatus updateMessageStatus(Long messageId, Long statusId) {
        if (!messageRepository.existsById(messageId)) {
            throw new ResourceNotFoundException("Message not found with id: " + messageId);
        }
        if (!statusRepository.existsById(statusId)) {
            throw new ResourceNotFoundException("Status not found with id: " + statusId);
        }

        MessageStatusId messageStatusId = new MessageStatusId(messageId, statusId);

        MessageStatus messageStatus = messageStatusRepository.findById(messageStatusId)
                .orElse(new MessageStatus(messageId, statusId));

        messageStatus.setUpdatedAt(LocalDateTime.now());
        return messageStatusRepository.save(messageStatus);
    }
}
