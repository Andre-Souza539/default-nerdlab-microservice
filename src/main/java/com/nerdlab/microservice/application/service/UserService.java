package com.nerdlab.microservice.application.service;

import com.nerdlab.microservice.domain.model.User;
import com.nerdlab.microservice.domain.port.in.CreateUserUseCase;
import com.nerdlab.microservice.domain.port.in.DeleteUserUseCase;
import com.nerdlab.microservice.domain.port.in.GetUserUseCase;
import com.nerdlab.microservice.domain.port.in.UpdateUserUseCase;
import com.nerdlab.microservice.domain.port.out.UserRepositoryPort;
import com.nerdlab.microservice.exception.BusinessException;
import com.nerdlab.microservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Application service implementing all User use cases.
 * Orchestrates domain logic and delegates to outbound ports.
 */
@Service
@Transactional
public class UserService implements CreateUserUseCase, GetUserUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User create(User user) {
        if (userRepositoryPort.existsByEmail(user.getEmail())) {
            throw new BusinessException("User with email '" + user.getEmail() + "' already exists");
        }

        user.setId(UUID.randomUUID());
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepositoryPort.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        return userRepositoryPort.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepositoryPort.findAll();
    }

    @Override
    public User update(UUID id, User user) {
        User existing = userRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setUpdatedAt(LocalDateTime.now());

        return userRepositoryPort.save(existing);
    }

    @Override
    public void delete(UUID id) {
        if (!userRepositoryPort.existsById(id)) {
            throw new ResourceNotFoundException("User", id);
        }
        userRepositoryPort.deleteById(id);
    }
}
