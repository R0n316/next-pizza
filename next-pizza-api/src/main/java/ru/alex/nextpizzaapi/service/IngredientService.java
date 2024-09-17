package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.IngredientRepository;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;
import ru.alex.nextpizzaapi.mapper.IngredientPreviewMapper;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientPreviewMapper ingredientPreviewMapper;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository,
                             IngredientPreviewMapper ingredientPreviewMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientPreviewMapper = ingredientPreviewMapper;
    }

    public List<IngredientPreviewDto> findAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientPreviewMapper::toDto)
                .toList();
    }
}
