package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.repository.ProductRepository;
import ru.alex.nextpizzaapi.dto.product.ProductPreviewDto;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;
import ru.alex.nextpizzaapi.mapper.product.ProductPreviewMapper;
import ru.alex.nextpizzaapi.mapper.product.ProductReadMapper;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPreviewMapper productPreviewMapper;
    private final ProductReadMapper productReadMapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductPreviewMapper productPreviewMapper,
                          ProductReadMapper productReadMapper) {
        this.productRepository = productRepository;
        this.productPreviewMapper = productPreviewMapper;
        this.productReadMapper = productReadMapper;
    }

    public List<ProductPreviewDto> findAllLikeName(String name) {
        return productRepository.findAllLikeName("%" + name + "%", Pageable.ofSize(5))
                .stream()
                .map(productPreviewMapper::toDto)
                .toList();
    }

    public ProductReadDto findById(Integer id) {
        // N + 1 solving
        productRepository.findAllWithProductItems();
        productRepository.findAllWithIngredients();
        return productRepository.findById(id)
                .map(productReadMapper::toDto)
                .orElse(null);
    }
}
