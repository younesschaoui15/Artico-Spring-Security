package com.chaoui.artico.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
