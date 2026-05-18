package com.usermanagement.repository;

import com.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link User} persistence operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by email address (used for uniqueness checks).
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether an email is already registered.
     */
    boolean existsByEmail(String email);

    /**
     * Checks whether an email is used by another user (exclude current id on update).
     */
    boolean existsByEmailAndIdNot(String email, Long id);
}
