package ru.alex.nextpizzaapi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Product;
import ru.alex.nextpizzaapi.database.repository.IngredientProductRepository;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;

import java.util.List;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    private final IngredientProductRepository ingredientProductRepository;

    @Autowired
    public ProductReadMapper(IngredientProductRepository ingredientProductRepository) {
        this.ingredientProductRepository = ingredientProductRepository;
    }

    @Override
    public ProductReadDto toDto(Product entity) {
        Pageable pageable = Pageable.ofSize(5);
        List<IngredientPreviewDto> ingredients = ingredientProductRepository
                .findAllByProduct(entity.getId(), pageable);
        return new ProductReadDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl(),
                entity.getCategory().getId(),
                ingredients
        );
    }
}
