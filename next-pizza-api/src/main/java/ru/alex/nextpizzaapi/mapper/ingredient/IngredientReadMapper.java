package ru.alex.nextpizzaapi.mapper.ingredient;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Ingredient;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientReadDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class IngredientReadMapper implements Mapper<Ingredient, IngredientReadDto> {
    @Override
    public IngredientReadDto toDto(Ingredient entity) {
        return new IngredientReadDto(
                entity.getId(),
                entity.getName(),
                entity.getPrice(),
                entity.getImageUrl()
        );
    }
}
