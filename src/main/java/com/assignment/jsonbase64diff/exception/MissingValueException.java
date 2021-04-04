package com.assignment.jsonbase64diff.exception;

/**
 * Custom exception to handle when the value is not provided in the left and/or right endpoints
 */
public class MissingValueException extends RuntimeException {
    public MissingValueException() {
        super("Missing value in body request");
    }
}
