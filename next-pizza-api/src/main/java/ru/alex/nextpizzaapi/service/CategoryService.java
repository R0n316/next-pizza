package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.entity.Category;
import ru.alex.nextpizzaapi.database.repository.CategoryRepository;
import ru.alex.nextpizzaapi.database.repository.ProductRepository;
import ru.alex.nextpizzaapi.dto.category.CategoryReadDto;
import ru.alex.nextpizzaapi.dto.category.ProductFilter;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;
import ru.alex.nextpizzaapi.mapper.CategoryReadMapper;
import ru.alex.nextpizzaapi.mapper.ProductReadMapper;
import ru.alex.nextpizzaapi.utils.CategoryUtils;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryReadMapper categoryReadMapper;
    private final ProductReadMapper productReadMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           CategoryReadMapper categoryReadMapper,
                           ProductReadMapper productReadMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryReadMapper = categoryReadMapper;
        this.productReadMapper = productReadMapper;
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

    public CategoryReadDto getRecommendedProducts(Integer categoryId) {
        // N + 1 solving
        productRepository.findAllWithIngredients();
        productRepository.findAllWithProductItems();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<ProductReadDto> products = productRepository.getRecommendedProducts(category.getId())
                    .stream()
                    .map(productReadMapper::toDto)
                    .toList();
            return new CategoryReadDto(
                    category.getId(),
                    category.getName(),
                    products
            );
        }
        return null;
    }
}
