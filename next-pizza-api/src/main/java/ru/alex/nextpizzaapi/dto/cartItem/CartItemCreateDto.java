package ru.alex.nextpizzaapi.dto.cartItem;

import java.util.List;

public record CartItemCreateDto(
        Integer productItemId,
        List<Integer> ingredientIds
) {
}
