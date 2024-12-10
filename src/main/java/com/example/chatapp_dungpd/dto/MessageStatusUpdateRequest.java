package com.example.chatapp_dungpd.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageStatusUpdateRequest {
    private Long messageId;
    private Long statusId;
    private String status;
    private LocalDateTime updatedAt = LocalDateTime.now();
}