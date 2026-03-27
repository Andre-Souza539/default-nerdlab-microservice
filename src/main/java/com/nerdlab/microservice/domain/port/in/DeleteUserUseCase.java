package com.nerdlab.microservice.domain.port.in;

import java.util.UUID;

/**
 * Inbound port for deleting a user.
 */
public interface DeleteUserUseCase {

    void delete(UUID id);
}
