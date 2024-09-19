package ru.alex.nextpizzaapi.dto.productItem;

import ru.alex.nextpizzaapi.database.entity.PizzaType;

public record ProductItemReadDto(
        Integer id,
        Integer price,
        Integer size,
        PizzaType pizzaType
) {
}
