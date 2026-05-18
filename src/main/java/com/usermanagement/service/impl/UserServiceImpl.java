package com.usermanagement.service.impl;

import com.usermanagement.dto.request.CreateUserRequest;
import com.usermanagement.dto.request.UpdateUserRequest;
import com.usermanagement.dto.response.PagedUserResponse;
import com.usermanagement.dto.response.UserResponse;
import com.usermanagement.entity.User;
import com.usermanagement.exception.DuplicateResourceException;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.mapper.UserMapper;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Default implementation of {@link UserService}.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("id", "firstName", "lastName", "email", "createdAt", "updatedAt");

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${app.pagination.max-size:100}")
    private int maxPageSize;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("User with email already exists: " + email);
        }
        User user = userMapper.toEntity(request);
        user.setEmail(email);
        User saved = userRepository.save(user);
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = findUserOrThrow(id);
        if (request.getEmail() != null) {
            String email = request.getEmail().trim().toLowerCase();
            if (userRepository.existsByEmailAndIdNot(email, id)) {
                throw new DuplicateResourceException("User with email already exists: " + email);
            }
        }
        userMapper.updateEntity(user, request);
        User updated = userRepository.save(user);
        return userMapper.toResponse(updated);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findUserOrThrow(id);
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return userMapper.toResponse(findUserOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedUserResponse getAllUsers(int page, int size, String sortBy, Sort.Direction direction) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1");
        }
        if (size > maxPageSize) {
            throw new IllegalArgumentException("Page size must not exceed " + maxPageSize);
        }
        String sortField = resolveSortField(sortBy);
        Sort sort = Sort.by(direction != null ? direction : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> users = userPage.getContent().stream()
                .map(userMapper::toResponse)
                .toList();

        return PagedUserResponse.builder()
                .users(users)
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .first(userPage.isFirst())
                .last(userPage.isLast())
                .build();
    }

    private User findUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private String resolveSortField(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "id";
        }
        String field = sortBy.trim();
        if (!ALLOWED_SORT_FIELDS.contains(field)) {
            throw new IllegalArgumentException(
                    "Invalid sort field: " + field + ". Allowed: " + ALLOWED_SORT_FIELDS);
        }
        return field;
    }
}
