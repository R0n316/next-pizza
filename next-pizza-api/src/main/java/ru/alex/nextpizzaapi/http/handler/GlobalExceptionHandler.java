package ru.alex.nextpizzaapi.http.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.alex.nextpizzaapi.dto.error.ErrorResponse;
import ru.alex.nextpizzaapi.exception.CartTokenNotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartTokenNotFoundException(CartTokenNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND.value(), System.currentTimeMillis(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(UNAUTHORIZED.value(), System.currentTimeMillis(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, UNAUTHORIZED);
    }
}
