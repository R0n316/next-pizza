package ru.alex.nextpizzaapi.mapper;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Ingredient;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;

@Component
public class IngredientPreviewMapper implements Mapper<Ingredient, IngredientPreviewDto>{

    @Override
    public IngredientPreviewDto toDto(Ingredient entity) {
        return new IngredientPreviewDto(
                entity.getId(),
                entity.getName(),
                entity.getPrice()
        );
    }
}
