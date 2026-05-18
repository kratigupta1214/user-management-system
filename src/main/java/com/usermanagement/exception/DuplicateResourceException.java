package com.usermanagement.exception;

/**
 * Thrown when creating or updating a resource would violate a uniqueness constraint.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
