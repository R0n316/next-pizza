package ru.alex.nextpizzaapi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Product;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemReadDto;

import java.util.List;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    private final ProductItemReadMapper productItemReadMapper;
    private final IngredientPreviewMapper ingredientPreviewMapper;

    @Autowired
    public ProductReadMapper(ProductItemReadMapper productItemReadMapper,
                             IngredientPreviewMapper ingredientPreviewMapper) {
        this.productItemReadMapper = productItemReadMapper;
        this.ingredientPreviewMapper = ingredientPreviewMapper;
    }

    @Override
    public ProductReadDto toDto(Product entity) {
        List<ProductItemReadDto> productItems = entity.getProductItems()
                .stream()
                .map(productItemReadMapper::toDto)
                .toList();

        List<IngredientPreviewDto> ingredients = entity.getIngredientProducts()
                .stream()
                .map(it -> ingredientPreviewMapper.toDto(it.getIngredient()))
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
