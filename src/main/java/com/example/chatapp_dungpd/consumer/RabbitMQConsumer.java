package com.example.chatapp_dungpd.consumer;
import com.example.chatapp_dungpd.model.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
@Service
public class RabbitMQConsumer {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public RabbitMQConsumer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = "chatQueue")
    public void receiveMessage(Message message) {
        // Gửi tin nhắn tới các client thông qua WebSocket
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }
}
