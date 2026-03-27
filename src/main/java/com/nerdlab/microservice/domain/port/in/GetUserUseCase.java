package com.nerdlab.microservice.domain.port.in;

import com.nerdlab.microservice.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Inbound port for retrieving users.
 */
public interface GetUserUseCase {

    Optional<User> findById(UUID id);

    List<User> findAll();
}
