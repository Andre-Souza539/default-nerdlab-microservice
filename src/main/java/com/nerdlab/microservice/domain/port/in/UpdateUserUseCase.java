package com.nerdlab.microservice.domain.port.in;

import com.nerdlab.microservice.domain.model.User;

import java.util.UUID;

/**
 * Inbound port for updating a user.
 */
public interface UpdateUserUseCase {

    User update(UUID id, User user);
}
