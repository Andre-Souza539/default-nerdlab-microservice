package com.nerdlab.microservice.adapter.out.persistence;

import com.nerdlab.microservice.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@ComponentScan(basePackages = "com.nerdlab.microservice.adapter.out.persistence")
class UserPersistenceAdapterTest {

    @Autowired
    private UserPersistenceAdapter userPersistenceAdapter;

    @Test
    void shouldSaveAndRetrieveUser() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        User user = new User(id, "John Doe", "john@example.com", true, now, now);

        User saved = userPersistenceAdapter.save(user);

        assertNotNull(saved);
        assertEquals(id, saved.getId());
        assertEquals("John Doe", saved.getName());
    }

    @Test
    void shouldFindUserById() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        User user = new User(id, "Jane Doe", "jane@example.com", true, now, now);
        userPersistenceAdapter.save(user);

        Optional<User> found = userPersistenceAdapter.findById(id);

        assertTrue(found.isPresent());
        assertEquals("Jane Doe", found.get().getName());
    }

    @Test
    void shouldReturnEmptyForNonExistentUser() {
        Optional<User> found = userPersistenceAdapter.findById(UUID.randomUUID());

        assertFalse(found.isPresent());
    }

    @Test
    void shouldFindAllUsers() {
        LocalDateTime now = LocalDateTime.now();
        userPersistenceAdapter.save(
                new User(UUID.randomUUID(), "User 1", "user1@example.com", true, now, now));
        userPersistenceAdapter.save(
                new User(UUID.randomUUID(), "User 2", "user2@example.com", true, now, now));

        List<User> users = userPersistenceAdapter.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void shouldDeleteUser() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        userPersistenceAdapter.save(
                new User(id, "To Delete", "delete@example.com", true, now, now));

        userPersistenceAdapter.deleteById(id);

        assertFalse(userPersistenceAdapter.existsById(id));
    }

    @Test
    void shouldCheckExistsByEmail() {
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        userPersistenceAdapter.save(
                new User(id, "Exists", "exists@example.com", true, now, now));

        assertTrue(userPersistenceAdapter.existsByEmail("exists@example.com"));
        assertFalse(userPersistenceAdapter.existsByEmail("notexists@example.com"));
    }
}
