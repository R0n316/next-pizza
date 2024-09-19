package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.ProductRepository;
import ru.alex.nextpizzaapi.dto.product.ProductPreviewDto;
import ru.alex.nextpizzaapi.mapper.ProductPreviewMapper;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPreviewMapper productPreviewMapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductPreviewMapper productPreviewMapper) {
        this.productRepository = productRepository;
        this.productPreviewMapper = productPreviewMapper;
    }

    public List<ProductPreviewDto> findAllLikeName(String name) {
        return productRepository.findAllLikeName("%" + name + "%", Pageable.ofSize(5))
                .stream()
                .map(productPreviewMapper::toDto)
                .toList();
    }
}
