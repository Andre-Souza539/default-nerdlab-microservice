package com.nerdlab.microservice.adapter.out.persistence.mapper;

import com.nerdlab.microservice.adapter.out.persistence.entity.UserEntity;
import com.nerdlab.microservice.domain.model.User;
import org.springframework.stereotype.Component;

/**
 * Maps between domain User model and JPA UserEntity.
 */
@Component
public class UserPersistenceMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        return new UserEntity(
                domain.getId(),
                domain.getName(),
                domain.getEmail(),
                domain.isActive(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}
