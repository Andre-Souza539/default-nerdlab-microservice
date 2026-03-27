package com.nerdlab.microservice.adapter.in.web;

import com.nerdlab.microservice.adapter.in.web.dto.CreateUserRequest;
import com.nerdlab.microservice.adapter.in.web.dto.UpdateUserRequest;
import com.nerdlab.microservice.adapter.in.web.dto.UserResponse;
import com.nerdlab.microservice.adapter.in.web.mapper.UserWebMapper;
import com.nerdlab.microservice.domain.model.User;
import com.nerdlab.microservice.domain.port.in.CreateUserUseCase;
import com.nerdlab.microservice.domain.port.in.DeleteUserUseCase;
import com.nerdlab.microservice.domain.port.in.GetUserUseCase;
import com.nerdlab.microservice.domain.port.in.UpdateUserUseCase;
import com.nerdlab.microservice.exception.ResourceNotFoundException;
import com.nerdlab.microservice.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for User CRUD operations.
 * Inbound adapter that delegates to domain use cases.
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserWebMapper mapper;

    public UserController(CreateUserUseCase createUserUseCase,
                          GetUserUseCase getUserUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          DeleteUserUseCase deleteUserUseCase,
                          UserWebMapper mapper) {
        this.createUserUseCase = createUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        User created = createUserUseCase.create(mapper.toDomain(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(mapper.toResponse(created), "User created successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id) {
        User user = getUserUseCase.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(user)));
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = getUserUseCase.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable UUID id,
                                                                 @Valid @RequestBody UpdateUserRequest request) {
        User updated = updateUserUseCase.update(id, mapper.toDomain(request));
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(updated), "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully"));
    }
}
