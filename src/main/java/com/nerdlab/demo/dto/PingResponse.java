package com.nerdlab.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PingResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}

