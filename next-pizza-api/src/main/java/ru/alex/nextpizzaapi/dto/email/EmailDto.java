package ru.alex.nextpizzaapi.dto.email;

import java.util.Map;

public record EmailDto(
        String to,
        String subject,
        String templateLocation,
        Map<String, Object> context
) {
}
