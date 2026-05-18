package com.usermanagement.exception;

/**
 * Thrown when a requested resource (e.g. user by id) does not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
