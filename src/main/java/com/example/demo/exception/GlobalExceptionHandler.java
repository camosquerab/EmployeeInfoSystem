package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientError(HttpClientErrorException ex) {
        if (ex.getStatusCode().value() == HttpStatus.TOO_MANY_REQUESTS.value()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Too many requests. Please try again later.");
        }
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleServerError(HttpServerErrorException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body("Server error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.badRequest().body("Invalid input. Please provide a valid number.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Invalid input: '%s' is not a valid %s",
                ex.getValue(),
                ex.getRequiredType().getSimpleName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}
