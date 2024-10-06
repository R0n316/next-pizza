package ru.alex.nextpizzaapi.mapper.ingredient;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Ingredient;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class IngredientPreviewMapper implements Mapper<Ingredient, IngredientPreviewDto> {

    @Override
    public IngredientPreviewDto toDto(Ingredient entity) {
        return new IngredientPreviewDto(
                entity.getId(),
                entity.getName(),
                entity.getPrice()
        );
    }
}
