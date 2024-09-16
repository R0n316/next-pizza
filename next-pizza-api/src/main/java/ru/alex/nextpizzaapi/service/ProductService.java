package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.ProductRepository;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;
import ru.alex.nextpizzaapi.mapper.ProductReadMapper;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductReadMapper productReadMapper;
    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductReadMapper productReadMapper) {
        this.productRepository = productRepository;
        this.productReadMapper = productReadMapper;
    }

    public List<ProductReadDto> findAllLikeName(String name) {
        return productRepository.findAllLikeName("%" + name + "%")
                .stream()
                .map(productReadMapper::toDto)
                .toList();
    }
}
