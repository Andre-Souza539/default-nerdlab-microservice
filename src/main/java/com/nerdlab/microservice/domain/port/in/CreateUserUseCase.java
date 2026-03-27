package com.nerdlab.microservice.domain.port.in;

import com.nerdlab.microservice.domain.model.User;

/**
 * Inbound port for creating a user.
 */
public interface CreateUserUseCase {

    User create(User user);
}
