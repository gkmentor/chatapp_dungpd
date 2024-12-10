package com.example.chatapp_dungpd.service;
import com.example.chatapp_dungpd.dto.MessageStatusUpdateRequest;
import com.example.chatapp_dungpd.exception.ResourceNotFoundException;
import com.example.chatapp_dungpd.model.MessageStatus;
import com.example.chatapp_dungpd.model.MessageStatusId;
import com.example.chatapp_dungpd.model.Status;
import com.example.chatapp_dungpd.repository.MessageStatusRepository;
import com.example.chatapp_dungpd.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class MessageStatusService {
    private final MessageStatusRepository messageStatusRepository;
    private final StatusRepository statusRepository;

    public MessageStatusService(MessageStatusRepository messageStatusRepository, StatusRepository statusRepository) {
        this.messageStatusRepository = messageStatusRepository;
        this.statusRepository = statusRepository;
    }
    public MessageStatus updateMessageStatus(MessageStatusUpdateRequest request) {
        MessageStatusId id = new MessageStatusId(request.getMessageId(), request.getStatusId());

        MessageStatus messageStatus = messageStatusRepository.findById(id)
                .orElse(new MessageStatus(request.getMessageId(), request.getStatusId()));
        Status status = statusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + request.getStatusId()));
        messageStatus.setStatus(status);


        messageStatus.setUpdatedAt(request.getUpdatedAt());

        return messageStatusRepository.save(messageStatus);
    }
    public void removeMessageStatus(Long messageId, Long statusId) {
        MessageStatusId id = new MessageStatusId(messageId, statusId);

        // Kiểm tra tồn tại trước khi xóa
        if (!messageStatusRepository.existsById(id)) {
            throw new ResourceNotFoundException("MessageStatus not found with the provided IDs.");
        }

        // Xóa bản ghi
        messageStatusRepository.deleteById(id);
    }
}
