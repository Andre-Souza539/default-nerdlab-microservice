package com.nerdlab.microservice.adapter.in.web.mapper;

import com.nerdlab.microservice.adapter.in.web.dto.CreateUserRequest;
import com.nerdlab.microservice.adapter.in.web.dto.UpdateUserRequest;
import com.nerdlab.microservice.adapter.in.web.dto.UserResponse;
import com.nerdlab.microservice.domain.model.User;
import org.springframework.stereotype.Component;

/**
 * Maps between web DTOs and domain model.
 */
@Component
public class UserWebMapper {

    public User toDomain(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }

    public User toDomain(UpdateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        return user;
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
