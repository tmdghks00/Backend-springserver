package com.example.smartplant.controller;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.HashMap;
import java.util.Map;
@Controller
public class FirebaseController {
    private final DatabaseReference dbRef;
    @Autowired
    public FirebaseController(FirebaseApp firebaseApp) {
        this.dbRef = FirebaseDatabase.getInstance(firebaseApp).getReference();
    }
    // 더미 데이터 생성 및 Firebase에 저장
    @Scheduled(fixedRate = 1000)
    public void generateDummyData() {
        Map<String, Object> data = new HashMap<>();
        data.put("temperature", Math.random() * 10 + 20);
        data.put("humidity", Math.random() * 20 + 40);
        data.put("soilMoisture", Math.random() * 50 + 50);
        data.put("timestamp", System.currentTimeMillis());
        dbRef.child("sensorData").push().setValueAsync(data).addListener(() -> {
            System.out.println("Firebase 데이터 저장 성공: " + data);
        }, Runnable::run);
    }
}