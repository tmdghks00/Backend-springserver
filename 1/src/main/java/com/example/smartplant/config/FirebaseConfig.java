package com.example.smartplant.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig { // Firebase를 Spring Boot 애플리케이션에서 사용할 수 있도록 설정하는 파일

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // firebase-service-account.json 파일 경로를 지정
        String firebaseServiceAccountPath = "C:/back/1/src/main/resources/firebase-service-account.json";

        // Firebase 서비스 계정 파일 경로로 파일을 읽어옵니다.
        try (FileInputStream serviceAccount = new FileInputStream(firebaseServiceAccountPath)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(System.getenv("FIREBASE_DATABASE_URL")) // 환경 변수로 DB URL 설정
                    .build();
            // FirebaseApp 이름을 명시적으로 "DEFAULT"로 지정, 이미 초기화된 경우 기존 인스턴스 반환
            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options, "DEFAULT");
            } else {
                return FirebaseApp.getInstance("DEFAULT");
            }
        }
    }
}
