package ru.alex.nextpizzaapi.exception;

import jakarta.persistence.EntityNotFoundException;

public class CartNotFoundException extends EntityNotFoundException {
    public CartNotFoundException(String message) {
      super(message);
    }
    public CartNotFoundException() {
      super("cart not found");
    }
}
