package com.maveric.projectcharter.exception;

import com.maveric.projectcharter.config.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Handles exceptions of type ApiRequestException and returns an appropriate response.
     *
     * @param exception The exception to be handled.
     * @return ResponseEntity containing the error details and status code.
     */
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (Constants.API_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    /**
     * Handles exceptions of type ServiceException and returns an appropriate response.
     *
     * @param exception The exception to be handled.
     * @return ResponseEntity containing the error details and status code.
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (Constants.SERVICE_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    /**
     * Handles exceptions of type NullPointerException and returns an appropriate response.
     *
     * @param exception The exception to be handled.
     * @return ResponseEntity containing the error details and status code.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                (Constants.NULL_POINTER_EXCEPTION, exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    /**
     * Handles exceptions of type MethodArgumentNotValidException and returns a map of field errors.
     *
     * @param ex The exception containing field validation errors.
     * @return Map containing field names as keys and corresponding error messages as values.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(UsernameNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                ("Username not found", exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                ("Authentication Exception", exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                ("You don't have access to view the page", exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                ("Your Session Expired", exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ExceptionMessage exceptionMessage = new ExceptionMessage
                ("Bad Credentials", exception.getMessage(), httpStatus);
        return new ResponseEntity<>(exceptionMessage, httpStatus);
    }
}
