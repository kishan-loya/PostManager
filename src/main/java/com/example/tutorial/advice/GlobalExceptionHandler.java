package com.example.tutorial.advice;

import com.example.tutorial.exception.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND);
        return createResponseEntity(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return createResponseEntity(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        return createResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        ApiError apiError = new ApiError("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        return createResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError apiError = ApiError.builder()
                .message("Validation failed")
                .statusCode(HttpStatus.BAD_REQUEST)
                .errors(errors)
                .build();
        return createResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> createResponseEntity(ApiError apiError) {
        ApiResponse<?> apiResponse = new ApiResponse<>(apiError);
        return new ResponseEntity<>(apiResponse, apiError.getStatusCode());
    }
}
