package com.chaoui.artico.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleException(AuthenticationException e) {
        e.printStackTrace();

        return new ResponseEntity<>(
            "Invalid Credentials : "+ e.getMessage(),
            HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleException(SQLException e) {
        e.printStackTrace();

        return new ResponseEntity<>(
            "Database error : "+ e.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
