package ru.alex.nextpizzaapi.dto.product;

import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;

import java.util.List;

public record ProductReadDto(
        Integer id,
        String name,
        String imageUrl,
        Integer categoryId,
        List<IngredientPreviewDto> ingredients
) {

}
