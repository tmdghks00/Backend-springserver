package com.example.smartplant.service;
import com.example.smartplant.config.CustomWebSocketHandler;
import com.example.smartplant.controller.FirebaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
@Service
public class DataGeneratorService {
    private final FirebaseController firebaseController;
    private final CustomWebSocketHandler webSocketHandler;
    @Autowired
    public DataGeneratorService(FirebaseController firebaseController, CustomWebSocketHandler webSocketHandler) {
        this.firebaseController = firebaseController;
        this.webSocketHandler = webSocketHandler;
    }
    @Scheduled(fixedRate = 1000)
    public void generateAndSendData() {
        // 더미 데이터 생성
        Map<String, Object> data = new HashMap<>();
        data.put("temperature", Math.random() * 10 + 20);
        data.put("humidity", Math.random() * 20 + 40);
        data.put("soilMoisture", Math.random() * 50 + 50);
        data.put("timestamp", System.currentTimeMillis());
        // Firebase에 저장
        firebaseController.generateDummyData();
        // WebSocket 클라이언트에게 데이터 전송
        webSocketHandler.sendMessageToAllClients(data);
    }
}