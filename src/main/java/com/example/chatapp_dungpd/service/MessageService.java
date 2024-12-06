package com.example.chatapp_dungpd.service;

import com.example.chatapp_dungpd.model.Message;
import com.example.chatapp_dungpd.model.MessageStatus;
import com.example.chatapp_dungpd.model.MessageStatusId;
import com.example.chatapp_dungpd.repository.MessageRepository;
import com.example.chatapp_dungpd.repository.MessageStatusRepository;
import com.example.chatapp_dungpd.repository.StatusRepository;
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

    // Constructor
    public MessageService(MessageRepository messageRepository,
                          MessageStatusRepository messageStatusRepository,
                          StatusRepository statusRepository) {
        this.messageRepository = messageRepository;
        this.messageStatusRepository = messageStatusRepository;
        this.statusRepository = statusRepository;
    }

    // Gửi tin nhắn
    public Message sendMessage(Long chatroomId, Long senderId, String content) {
        Message message = new Message();
        message.setChatroomId(chatroomId);
        message.setSenderId(senderId);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    //to create a new message
    public Message createMessage(Message message) {
        return messageRepository.save(message);  // Save message to DB and return
    }

    // 2. Method to get messages by chatroomId
    public List<Message> getMessagesByChatroomId(Long chatroomId) {
        return messageRepository.findByChatroomId(chatroomId);  // Assuming you have a custom query in the repository
    }

    // 3. Method to delete a message by ID
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

    // Cập nhật trạng thái tin nhắn (đã đọc, chưa đọc, v.v.)
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
