package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.repository.IngredientRepository;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientReadDto;
import ru.alex.nextpizzaapi.mapper.ingredient.IngredientReadMapper;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientReadMapper ingredientReadMapper;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository,
                             IngredientReadMapper ingredientReadMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientReadMapper = ingredientReadMapper;
    }

    public List<IngredientReadDto> findAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(ingredientReadMapper::toDto)
                .toList();
    }
}
