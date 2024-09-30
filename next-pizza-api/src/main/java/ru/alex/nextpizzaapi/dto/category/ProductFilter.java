package ru.alex.nextpizzaapi.dto.category;

import java.util.List;

public record ProductFilter(
        List<Integer> pizzaTypes,
        List<Integer> sizes,
        Integer priceFrom,
        Integer priceTo,
        List<Integer> ingredients
) {
}
