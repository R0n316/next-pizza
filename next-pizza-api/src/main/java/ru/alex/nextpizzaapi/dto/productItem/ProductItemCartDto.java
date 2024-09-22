package ru.alex.nextpizzaapi.dto.productItem;

import ru.alex.nextpizzaapi.database.entity.PizzaType;
import ru.alex.nextpizzaapi.dto.product.ProductPreviewDto;

public record ProductItemCartDto(
        Integer id,
        Integer price,
        Integer size,
        PizzaType pizzaType,
        ProductPreviewDto product
) {
}
