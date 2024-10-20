package ru.alex.nextpizzaapi.http.handler;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.alex.nextpizzaapi.dto.error.ErrorResponse;
import ru.alex.nextpizzaapi.exception.CartTokenNotFoundException;
import ru.alex.nextpizzaapi.exception.EmailAlreadyInUseException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCartTokenNotFoundException(CartTokenNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, UNAUTHORIZED);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(EmailAlreadyInUseException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<ErrorResponse> handleJWTVerificationException(JWTVerificationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                System.currentTimeMillis(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
}
