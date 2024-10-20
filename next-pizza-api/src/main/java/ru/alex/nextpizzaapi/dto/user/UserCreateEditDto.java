package ru.alex.nextpizzaapi.dto.user;

public record UserCreateEditDto(
        String email,
        String password,
        String fullName
) {
}
