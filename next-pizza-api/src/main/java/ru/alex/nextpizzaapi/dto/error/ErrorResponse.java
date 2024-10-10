package ru.alex.nextpizzaapi.dto.error;

public record ErrorResponse(
        int responseStatus,
        long timestamp,
        String message
) {
}
