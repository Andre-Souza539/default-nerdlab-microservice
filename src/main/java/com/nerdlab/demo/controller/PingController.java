package com.nerdlab.demo.controller;

import com.nerdlab.demo.dto.PingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class PingController {

    private static final String PROJECT_PURPOSE = "Scaffold for microservices using Spring Boot and some generic dependencies";

    @GetMapping("/ping")
    public ResponseEntity<PingResponse> ping() {
        PingResponse response = PingResponse.builder()
                .message("Pong! Welcome to the NerdLab Microservice")
                .purpose(PROJECT_PURPOSE)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/Hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, World!");
    }

    @GetMapping("/sum")
    public ResponseEntity<String> sum(){
        return ResponseEntity.ok("A² + B² = C²");
    }
}
