package ru.alex.nextpizzaapi.mapper.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Category;
import ru.alex.nextpizzaapi.dto.category.CategoryReadDto;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;
import ru.alex.nextpizzaapi.mapper.Mapper;
import ru.alex.nextpizzaapi.mapper.product.ProductReadMapper;

import java.util.List;

@Component
public class CategoryReadMapper implements Mapper<Category, CategoryReadDto> {
    private final ProductReadMapper productReadMapper;

    @Autowired
    public CategoryReadMapper(ProductReadMapper productReadMapper) {
        this.productReadMapper = productReadMapper;
    }

    @Override
    public CategoryReadDto toDto(Category entity) {
        List<ProductReadDto> products = entity.getProducts()
                .stream()
                .map(productReadMapper::toDto)
                .toList();

        return new CategoryReadDto(
                entity.getId(),
                entity.getName(),
                products
        );
    }
}
