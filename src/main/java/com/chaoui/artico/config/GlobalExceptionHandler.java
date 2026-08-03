package com.chaoui.artico.config;

import com.chaoui.artico.dto.response.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorDTO> handleException(AuthenticationException e) {
        e.printStackTrace();

        var httpStatus = HttpStatus.UNAUTHORIZED;

        ApiErrorDTO error = new ApiErrorDTO(
            Instant.now(),
            httpStatus.value(),
            "Invalid username or password"
        );

        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiErrorDTO> handleException(SQLException e) {
        e.printStackTrace();

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiErrorDTO error = new ApiErrorDTO(
            Instant.now(),
            httpStatus.value(),
            "Internal server error"
        );

        return ResponseEntity.status(httpStatus).body(error);
    }
}
