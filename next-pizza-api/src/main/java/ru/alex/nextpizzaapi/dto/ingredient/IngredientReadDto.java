package ru.alex.nextpizzaapi.dto.ingredient;

public record IngredientReadDto(
        Integer id,
        String name,
        Integer price,
        String imageUrl
) {
}
