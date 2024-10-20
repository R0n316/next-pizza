package ru.alex.nextpizzaapi.dto.user;

public record UserReadDto(
        Integer id,
        String fullName,
        String email
) {
}
