package com.example.smartplant.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig { // Firebase를 Spring Boot 애플리케이션에서 사용할 수 있도록 설정하는 파일

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // 환경 변수에서 Firebase 서비스 계정 파일 경로를 가져옵니다.
        String firebaseServiceAccountPath = System.getenv("FIREBASE_SERVICE_ACCOUNT_PATH");

        // 환경 변수가 설정되어 있지 않으면 기본 클래스패스에서 찾습니다.
        Resource resource;
        if (firebaseServiceAccountPath != null && !firebaseServiceAccountPath.isEmpty()) {
            // 환경 변수로 지정된 경로에서 파일을 읽습니다.
            resource = new ClassPathResource(firebaseServiceAccountPath);
        } else {
            // 기본 경로인 classpath에서 firebase-service-account.json 파일을 찾습니다.
            resource = new ClassPathResource("firebase-service-account.json");
        }

        try (InputStream serviceAccount = resource.getInputStream()) {
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
