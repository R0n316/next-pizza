package ru.alex.nextpizzaapi.dto.error;

public record ErrorResponse(
        long timestamp,
        String message
) {
}
