package com.nerdlab.microservice.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nerdlab.microservice.adapter.in.web.dto.CreateUserRequest;
import com.nerdlab.microservice.adapter.in.web.dto.UpdateUserRequest;
import com.nerdlab.microservice.adapter.in.web.mapper.UserWebMapper;
import com.nerdlab.microservice.domain.model.User;
import com.nerdlab.microservice.domain.port.in.CreateUserUseCase;
import com.nerdlab.microservice.domain.port.in.DeleteUserUseCase;
import com.nerdlab.microservice.domain.port.in.GetUserUseCase;
import com.nerdlab.microservice.domain.port.in.UpdateUserUseCase;
import com.nerdlab.microservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateUserUseCase createUserUseCase;

    @MockitoBean
    private GetUserUseCase getUserUseCase;

    @MockitoBean
    private UpdateUserUseCase updateUserUseCase;

    @MockitoBean
    private DeleteUserUseCase deleteUserUseCase;

    @MockitoBean
    private UserWebMapper mapper;

    private final UUID testId = UUID.randomUUID();
    private final LocalDateTime now = LocalDateTime.now();

    private User createTestUser() {
        return new User(testId, "John Doe", "john@example.com", true, now, now);
    }

    @Test
    void shouldCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest("John Doe", "john@example.com");
        User user = createTestUser();

        when(mapper.toDomain(any(CreateUserRequest.class))).thenReturn(user);
        when(createUserUseCase.create(any(User.class))).thenReturn(user);
        when(mapper.toResponse(any(User.class))).thenReturn(
                new com.nerdlab.microservice.adapter.in.web.dto.UserResponse(
                        testId, "John Doe", "john@example.com", true, now, now));

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.message").value("User created successfully"));
    }

    @Test
    void shouldReturnBadRequestForInvalidInput() throws Exception {
        CreateUserRequest request = new CreateUserRequest("", "invalid-email");

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetUserById() throws Exception {
        User user = createTestUser();

        when(getUserUseCase.findById(testId)).thenReturn(Optional.of(user));
        when(mapper.toResponse(any(User.class))).thenReturn(
                new com.nerdlab.microservice.adapter.in.web.dto.UserResponse(
                        testId, "John Doe", "john@example.com", true, now, now));

        mockMvc.perform(get("/api/v1/users/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("John Doe"));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        when(getUserUseCase.findById(testId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/{id}", testId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        User user = createTestUser();

        when(getUserUseCase.findAll()).thenReturn(List.of(user));
        when(mapper.toResponse(any(User.class))).thenReturn(
                new com.nerdlab.microservice.adapter.in.web.dto.UserResponse(
                        testId, "John Doe", "john@example.com", true, now, now));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("John Doe"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("Jane Doe", "jane@example.com");
        User updatedUser = new User(testId, "Jane Doe", "jane@example.com", true, now, now);

        when(mapper.toDomain(any(UpdateUserRequest.class))).thenReturn(updatedUser);
        when(updateUserUseCase.update(eq(testId), any(User.class))).thenReturn(updatedUser);
        when(mapper.toResponse(any(User.class))).thenReturn(
                new com.nerdlab.microservice.adapter.in.web.dto.UserResponse(
                        testId, "Jane Doe", "jane@example.com", true, now, now));

        mockMvc.perform(put("/api/v1/users/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Jane Doe"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(deleteUserUseCase).delete(testId);

        mockMvc.perform(delete("/api/v1/users/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"));
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentUser() throws Exception {
        doThrow(new ResourceNotFoundException("User", testId))
                .when(deleteUserUseCase).delete(testId);

        mockMvc.perform(delete("/api/v1/users/{id}", testId))
                .andExpect(status().isNotFound());
    }
}
