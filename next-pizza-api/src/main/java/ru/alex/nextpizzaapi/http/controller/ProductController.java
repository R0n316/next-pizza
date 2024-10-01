package ru.alex.nextpizzaapi.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alex.nextpizzaapi.dto.product.ProductPreviewDto;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;
import ru.alex.nextpizzaapi.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductPreviewDto> findAll(@RequestParam(value = "name", required = false) String name) {
        return productService.findAllLikeName(name);
    }

    @GetMapping("/{id}")
    public ProductReadDto findById(@PathVariable("id") Integer id) {
        return productService.findById(id);
    }
}