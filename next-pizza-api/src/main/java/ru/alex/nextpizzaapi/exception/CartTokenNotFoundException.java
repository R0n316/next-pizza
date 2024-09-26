package ru.alex.nextpizzaapi.exception;

import jakarta.persistence.EntityNotFoundException;

public class CartTokenNotFoundException extends EntityNotFoundException {
  public CartTokenNotFoundException(String message) {
    super(message);
  }
}
