package com.usermanagement.mapper;

import com.usermanagement.dto.request.CreateUserRequest;
import com.usermanagement.dto.request.UpdateUserRequest;
import com.usermanagement.dto.response.UserResponse;
import com.usermanagement.entity.User;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link User} entity and request/response DTOs.
 * Keeps controllers and services free of manual field copying.
 */
@Component
public class UserMapper {

    public User toEntity(CreateUserRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail().trim().toLowerCase())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();
    }

    public void updateEntity(User user, UpdateUserRequest request) {
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail().trim().toLowerCase());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .dateOfBirth(user.getDateOfBirth())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
