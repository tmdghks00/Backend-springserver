package com.example.smartplant.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("WebSocket 연결 성공: " + session.getRemoteAddress());
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("수신된 메시지: " + message.getPayload());
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        System.out.println("WebSocket 연결 종료: " + session.getRemoteAddress());
    }
    public void sendMessageToAllClients(Map<String, Object> data) {
        String jsonData;
        try {
            jsonData = objectMapper.writeValueAsString(data);
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(jsonData));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}