package ru.alex.nextpizzaapi.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("user with this email already exists");
    }
}
