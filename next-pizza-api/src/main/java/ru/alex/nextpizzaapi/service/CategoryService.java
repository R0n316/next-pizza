package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.CategoryRepository;
import ru.alex.nextpizzaapi.database.repository.ProductRepository;
import ru.alex.nextpizzaapi.dto.category.CategoryReadDto;
import ru.alex.nextpizzaapi.mapper.CategoryReadMapper;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryReadMapper categoryReadMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           CategoryReadMapper categoryReadMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryReadMapper = categoryReadMapper;
    }

    public List<CategoryReadDto> findAll() {
        productRepository.findAllWithProductItems();
        productRepository.findAllWithIngredients();
        return categoryRepository.findAllWithProducts()
                .stream()
                .map(categoryReadMapper::toDto)
                .toList();
    }
}
