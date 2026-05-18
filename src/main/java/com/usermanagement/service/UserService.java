package com.usermanagement.service;

import com.usermanagement.dto.request.CreateUserRequest;
import com.usermanagement.dto.request.UpdateUserRequest;
import com.usermanagement.dto.response.PagedUserResponse;
import com.usermanagement.dto.response.UserResponse;
import org.springframework.data.domain.Sort;

/**
 * Business logic contract for user operations.
 */
public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    UserResponse getUserById(Long id);

    PagedUserResponse getAllUsers(int page, int size, String sortBy, Sort.Direction direction);
}
