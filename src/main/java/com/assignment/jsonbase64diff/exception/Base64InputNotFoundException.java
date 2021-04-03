package com.assignment.jsonbase64diff.exception;

public class Base64InputNotFoundException extends RuntimeException {
    public Base64InputNotFoundException(String id) {
        super(String.format("Input with Id %s not found", id));
    }
}
