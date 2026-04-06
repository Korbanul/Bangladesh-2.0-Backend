package com.bangladesh20.backend.Error;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> WrongUsernameorPasswordErrors(BadCredentialsException exception, HttpServletRequest request) {
        log.warn("Failed login attempt at: {}", request.getRequestURI());

       ApiError apiError= ApiError.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error(exception.getMessage())
                .errorMessage("Wrong username or password")
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(apiError.getStatusCode()).body(apiError);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> JWTTokenExpairError(ExpiredJwtException exception, HttpServletRequest request) {
        log.warn("Token Expair: {}", request.getRequestURI());
        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error(exception.getMessage())
                .errorMessage("Session expired. Please login again.")
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(apiError);
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> JWTTokenErrors(JwtException exception, HttpServletRequest request) {
        log.warn("Invalid JWT at: {}", request.getRequestURI());
        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error(exception.getMessage())
                .errorMessage("Invalid or malformed token.")
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(apiError.getStatusCode()).body(apiError);
    }

    // ACCESS ERRORS

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> AccessDeniedError(AccessDeniedException exception, HttpServletRequest request) {
        log.warn("Access denied at: {} ", request.getRequestURI());
        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .error(exception.getMessage())
                .errorMessage("You don't have permission.")
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(apiError);


    }

    // VALIDATION ERRORS
    // When @Valid on @RequestBody fails → this fires
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> InputValidationError(MethodArgumentNotValidException exception, HttpServletRequest request) {
        log.warn("Validation failed at: {}", request.getRequestURI());

        // Collect all field errors into one message
        String fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(fieldErrors)
                .errorMessage("Validation Failed")
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiError);
    }


    // @Validated on @PathVariable / @RequestParam fails → this fires


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException exception, HttpServletRequest request) {

        String errorMessages = exception.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));
        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(errorMessages)
                .errorMessage("Constraint Violation")
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiError);
    }

    // NOT FOUND ERRORS
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> NotFoundError(EntityNotFoundException exception, HttpServletRequest request) {
        log.warn("Entity not found at: {}", request.getRequestURI());
        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error(exception.getMessage())
                .errorMessage("Resource Not Found")
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiError);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> NotFoundError2(NoSuchElementException exception, HttpServletRequest request) {
        log.warn("Entity not found at: {}", request.getRequestURI());
        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error(exception.getMessage())
                .errorMessage("Resource Not Found")
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiError);
    }

    // CONFLICT ERRORS If user Already Exists.
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(
            UserAlreadyExistsException exception, HttpServletRequest request) {
        log.warn("Duplicate user attempt at: {}", request.getRequestURI());
        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .error(exception.getMessage())
                .errorMessage("User with this username already exists.")
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(apiError);
    }
    // GlobalExceptionHandler.java
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(
            RuntimeException exception, HttpServletRequest request) {
        log.error("Runtime error at: {}", request.getRequestURI(), exception);
        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error(exception.getMessage())
                .errorMessage("Resource not found.")
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiError);
    }


    // Duplicate email, username, unique constraint violation in DB
    @ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<ApiError> HandleUniqueValueError(DataIntegrityViolationException exception,HttpServletRequest request)
    {
        log.error("Data integrity violation at: {}", request.getRequestURI());
        ApiError apiError= ApiError.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .error(exception.getMostSpecificCause().getMessage())
                .errorMessage("Value must be unique")
                .timeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(apiError);
    }

    // CATCH-ALL ERRORS — If No of the above then this will call

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception exception, HttpServletRequest request) {

        // Log full stack trace for unknown errors
        log.error("Unexpected error at: {}", request.getRequestURI(), exception);

        ApiError apiError = ApiError.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(exception.getMessage())
                .errorMessage("Something went wrong. Please try again later.")
                .timeStamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiError);
    }


}
