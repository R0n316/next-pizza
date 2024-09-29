package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.repository.CategoryRepository;
import ru.alex.nextpizzaapi.database.repository.ProductRepository;
import ru.alex.nextpizzaapi.dto.category.CategoryReadDto;
import ru.alex.nextpizzaapi.dto.category.ProductFilter;
import ru.alex.nextpizzaapi.mapper.CategoryReadMapper;
import ru.alex.nextpizzaapi.utils.CategoryUtils;

import java.util.List;


@Service
@Transactional(readOnly = true)
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

    public List<CategoryReadDto> findAll(ProductFilter productFilter) {
        productRepository.findAllWithProductItems();
        productRepository.findAllWithIngredients();
        List<CategoryReadDto> categories = categoryRepository.findAllWithProducts()
                .stream()
                .map(categoryReadMapper::toDto)
                .toList();

        return CategoryUtils.filterCategories(categories, productFilter);
    }
}
