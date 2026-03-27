package com.nerdlab.microservice.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void shouldCreateUserWithAllFields() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        User user = new User(id, "John Doe", "john@example.com", true, now, now);

        assertEquals(id, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertTrue(user.isActive());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void shouldActivateUser() {
        User user = new User();
        user.setActive(false);

        user.activate();

        assertTrue(user.isActive());
    }

    @Test
    void shouldDeactivateUser() {
        User user = new User();
        user.setActive(true);

        user.deactivate();

        assertFalse(user.isActive());
    }

    @Test
    void shouldBeEqualWhenSameId() {
        UUID id = UUID.randomUUID();
        User user1 = new User(id, "John", "john@example.com", true, null, null);
        User user2 = new User(id, "Jane", "jane@example.com", false, null, null);

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenDifferentId() {
        User user1 = new User(UUID.randomUUID(), "John", "john@example.com", true, null, null);
        User user2 = new User(UUID.randomUUID(), "John", "john@example.com", true, null, null);

        assertNotEquals(user1, user2);
    }

    @Test
    void shouldReturnMeaningfulToString() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "John", "john@example.com", true, null, null);

        String result = user.toString();

        assertTrue(result.contains("John"));
        assertTrue(result.contains("john@example.com"));
    }
}
