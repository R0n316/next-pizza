package ru.alex.nextpizzaapi.dto.order;

public record OrderCreateDto(
        String fullName,
        String email,
        String phone,
        String comment,
        String address
) {
}