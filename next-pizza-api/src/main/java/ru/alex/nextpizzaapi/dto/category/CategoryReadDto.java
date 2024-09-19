package ru.alex.nextpizzaapi.dto.category;

import ru.alex.nextpizzaapi.dto.product.ProductReadDto;

import java.util.List;

public record CategoryReadDto(
        Integer id,
        String name,
        List<ProductReadDto> products
) {

}

/*
    1. Делать запрос на получение всех категорий
    2. делаем запрос на получение всех продуктов по категории
        2.1 маппим продукты
            2.1.1 для каждого продукта получаем его productItems
    3. делаем запрос в таблицу many-to-many на получение

    SELECT*
    FROM category

    SELECT*
    FROM product
    WHERE category_id = ?

    SELECT*
    FROM product_item
    WHERE product_id = ? -- N + 1?
 */