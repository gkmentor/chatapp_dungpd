package com.example.chatapp_dungpd.controller;


import com.example.chatapp_dungpd.model.Message;
import com.example.chatapp_dungpd.producer.RabbitMQProducer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final RabbitMQProducer rabbitMQProducer;

    public WebSocketController(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @MessageMapping("/send")
    public void sendMessage(Message message) {
        rabbitMQProducer.sendMessage(message);
    }
}
