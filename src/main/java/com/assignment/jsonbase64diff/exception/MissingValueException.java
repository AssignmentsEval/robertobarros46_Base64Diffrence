package com.assignment.jsonbase64diff.exception;

public class MissingValueException extends RuntimeException {
    public MissingValueException() {
        super("Missing value in body request");
    }
}
