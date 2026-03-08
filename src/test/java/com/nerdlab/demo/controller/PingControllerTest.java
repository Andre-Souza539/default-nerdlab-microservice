package com.nerdlab.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPingEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/ping")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Pong! Welcome to the NerdLab Microservice"))
                .andExpect(jsonPath("$.purpose").value("Scaffold for microservices using Spring Boot and some generic dependencies"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testPingEndpointReturnsValidResponse() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/ping")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void testPingEndpointTimestampIsNotNull() throws Exception {
        mockMvc.perform(get("/api/v1/ping")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}

