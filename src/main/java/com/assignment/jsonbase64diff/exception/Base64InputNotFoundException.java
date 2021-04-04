package com.assignment.jsonbase64diff.exception;

/**
 * Custom exception to handle when the provided id to compare was not found, it extends RuntimeException
 */
public class Base64InputNotFoundException extends RuntimeException {
    public Base64InputNotFoundException(String id) {
        super(String.format("Input with Id %s not found", id));
    }
}
