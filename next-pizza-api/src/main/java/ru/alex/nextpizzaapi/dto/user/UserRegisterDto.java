package ru.alex.nextpizzaapi.dto.user;

public record UserRegisterDto(
        String email,
        String password,
        String fullName
) {
}
