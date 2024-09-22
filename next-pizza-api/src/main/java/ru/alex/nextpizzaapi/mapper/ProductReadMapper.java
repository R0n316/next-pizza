package ru.alex.nextpizzaapi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Product;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientReadDto;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemReadDto;

import java.util.Comparator;
import java.util.List;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    private final ProductItemReadMapper productItemReadMapper;
    private final IngredientReadMapper ingredientReadMapper;

    @Autowired
    public ProductReadMapper(ProductItemReadMapper productItemReadMapper,
                             IngredientReadMapper ingredientReadMapper) {
        this.productItemReadMapper = productItemReadMapper;
        this.ingredientReadMapper = ingredientReadMapper;
    }

    @Override
    public ProductReadDto toDto(Product entity) {
        List<ProductItemReadDto> productItems = entity.getProductItems()
                .stream()
                .map(productItemReadMapper::toDto)
                .sorted(Comparator.comparing(ProductItemReadDto::price))
                .toList();

        List<IngredientReadDto> ingredients = entity.getIngredientProducts()
                .stream()
                .map(it -> ingredientReadMapper.toDto(it.getIngredient()))
                .toList();

        return new ProductReadDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                productItems,
                ingredients
        );
    }
}
