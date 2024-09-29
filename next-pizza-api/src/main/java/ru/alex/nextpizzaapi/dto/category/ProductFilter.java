package ru.alex.nextpizzaapi.dto.category;

import ru.alex.nextpizzaapi.database.entity.PizzaType;

import java.util.List;

public record ProductFilter(
        List<PizzaType> pizzaTypes,
        List<Integer> sizes,
        Integer priceFrom,
        Integer priceTo,
        List<Integer> ingredients
) {
}
