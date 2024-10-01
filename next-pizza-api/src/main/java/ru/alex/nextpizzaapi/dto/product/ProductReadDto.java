package ru.alex.nextpizzaapi.dto.product;

import ru.alex.nextpizzaapi.dto.ingredient.IngredientReadDto;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemReadDto;

import java.util.List;

public record ProductReadDto(
        Integer id,
        String name,
        String imageUrl,
        Integer categoryId,
        List<ProductItemReadDto> items,
        List<IngredientReadDto> ingredients
) {
}
