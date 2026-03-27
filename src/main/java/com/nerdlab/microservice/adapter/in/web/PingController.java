package com.nerdlab.microservice.adapter.in.web;

import com.nerdlab.microservice.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Health check / ping controller.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Health", description = "Health check endpoints")
public class PingController {

    private static final String PROJECT_PURPOSE =
            "Scaffold for microservices using Spring Boot and some generic dependencies";

    @GetMapping("/ping")
    @Operation(summary = "Health check endpoint")
    public ResponseEntity<ApiResponse<Map<String, String>>> ping() {
        Map<String, String> data = Map.of(
                "message", "Pong! Welcome to the NerdLab Microservice",
                "purpose", PROJECT_PURPOSE
        );
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/hello")
    @Operation(summary = "Hello world endpoint")
    public ResponseEntity<ApiResponse<String>> hello() {
        return ResponseEntity.ok(ApiResponse.success("Hello, World!"));
    }
}
