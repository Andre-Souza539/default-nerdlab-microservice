package com.nerdlab.microservice.application.service;

import com.nerdlab.microservice.domain.model.User;
import com.nerdlab.microservice.domain.port.out.UserRepositoryPort;
import com.nerdlab.microservice.exception.BusinessException;
import com.nerdlab.microservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testUser = new User(testId, "John Doe", "john@example.com", true,
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void shouldCreateUserSuccessfully() {
        User newUser = new User();
        newUser.setName("John Doe");
        newUser.setEmail("john@example.com");

        when(userRepositoryPort.existsByEmail("john@example.com")).thenReturn(false);
        when(userRepositoryPort.save(any(User.class))).thenReturn(testUser);

        User result = userService.create(newUser);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userRepositoryPort).save(any(User.class));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        User newUser = new User();
        newUser.setEmail("john@example.com");

        when(userRepositoryPort.existsByEmail("john@example.com")).thenReturn(true);

        assertThrows(BusinessException.class, () -> userService.create(newUser));
        verify(userRepositoryPort, never()).save(any());
    }

    @Test
    void shouldFindUserById() {
        when(userRepositoryPort.findById(testId)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findById(testId);

        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        when(userRepositoryPort.findById(testId)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(testId);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindAllUsers() {
        when(userRepositoryPort.findAll()).thenReturn(List.of(testUser));

        List<User> result = userService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        User updateData = new User();
        updateData.setName("Jane Doe");
        updateData.setEmail("jane@example.com");

        when(userRepositoryPort.findById(testId)).thenReturn(Optional.of(testUser));
        when(userRepositoryPort.save(any(User.class))).thenReturn(testUser);

        User result = userService.update(testId, updateData);

        assertNotNull(result);
        verify(userRepositoryPort).save(any(User.class));
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentUser() {
        when(userRepositoryPort.findById(testId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.update(testId, new User()));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        when(userRepositoryPort.existsById(testId)).thenReturn(true);

        userService.delete(testId);

        verify(userRepositoryPort).deleteById(testId);
    }

    @Test
    void shouldThrowWhenDeletingNonExistentUser() {
        when(userRepositoryPort.existsById(testId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> userService.delete(testId));
    }
}
