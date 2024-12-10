package com.example.chatapp_dungpd.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Kích hoạt broker nội bộ với prefix /topic
        registry.setApplicationDestinationPrefixes("/app"); // Prefix cho client gửi message tới server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Endpoint cho WebSocket
                .setAllowedOrigins("*") // Cho phép kết nối từ bất kỳ origin nào (tuỳ chỉnh theo nhu cầu)
                .withSockJS(); // Hỗ trợ fallback cho trình duyệt không hỗ trợ WebSocket
    }
}