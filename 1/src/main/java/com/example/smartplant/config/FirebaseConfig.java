package com.example.smartplant.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        // 1. firebase-service-account.json 파일 내용 전체를 아래 FIREBASE_CREDENTIALS 변수에 문자열로 넣어줍니다.
        String firebaseCredentials = System.getenv("FIREBASE_CREDENTIALS");

        // 2. 문자열을 InputStream으로 변환합니다.
        try (InputStream serviceAccount = new ByteArrayInputStream(firebaseCredentials.getBytes(StandardCharsets.UTF_8))) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(System.getenv("FIREBASE_DATABASE_URL")) // 환경 변수로 DB URL 설정
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                return FirebaseApp.initializeApp(options, "DEFAULT");
            } else {
                return FirebaseApp.getInstance("DEFAULT");
            }
        }
    }
}