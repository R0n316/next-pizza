package ru.alex.nextpizzaapi.dto.category;

import ru.alex.nextpizzaapi.dto.product.ProductReadDto;

import java.util.List;

public record CategoryReadDto(
        Integer id,
        String name,
        List<ProductReadDto> products
) {

}