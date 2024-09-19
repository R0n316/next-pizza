package ru.alex.nextpizzaapi.dto.product;

import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemReadDto;

import java.util.List;

public record ProductReadDto(
        Integer id,
        String name,
        String imageUrl,
        List<ProductItemReadDto> items,
        List<IngredientPreviewDto> ingredients
) {
}
