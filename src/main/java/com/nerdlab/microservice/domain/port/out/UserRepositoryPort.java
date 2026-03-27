package com.nerdlab.microservice.domain.port.out;

import com.nerdlab.microservice.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Outbound port for user persistence operations.
 * This interface is implemented by the persistence adapter.
 */
public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    void deleteById(UUID id);

    boolean existsById(UUID id);

    boolean existsByEmail(String email);
}
