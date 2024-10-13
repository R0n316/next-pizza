package ru.alex.nextpizzaapi.dto.user;

public record UserLoginDto(
        String email,
        String password
) {
}
