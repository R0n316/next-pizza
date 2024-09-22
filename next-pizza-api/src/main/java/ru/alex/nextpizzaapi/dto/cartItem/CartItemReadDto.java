package ru.alex.nextpizzaapi.dto.cartItem;

import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemCartDto;

import java.time.Instant;
import java.util.List;

public record CartItemReadDto(
        Integer id,
        ProductItemCartDto productItem,
        List<IngredientPreviewDto> ingredients,
        Integer quantity,
        Instant createdAt
) {
}
