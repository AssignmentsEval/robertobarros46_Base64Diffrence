package com.assignment.jsonbase64diff.controller.exceptionhandler;

import com.assignment.jsonbase64diff.exception.Base64InputNotFoundException;
import com.assignment.jsonbase64diff.exception.MissingValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Advice controller to handle the error in endpoints in a global manner
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Base64InputNotFoundException.class)
    public ResponseEntity<Object> handleBase64InputNotFound (
            Base64InputNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Input nof found, please provide a valid id");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingValueException.class)
    public ResponseEntity<Object> handleMissingValueException (
            MissingValueException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Missing value in body request");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}