package ru.alex.nextpizzaapi.dto.product;

public record ProductReadDto(
        Integer id,
        String name,
        String imageUrl
) {
}
