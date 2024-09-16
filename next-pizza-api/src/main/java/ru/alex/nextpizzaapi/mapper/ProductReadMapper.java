package ru.alex.nextpizzaapi.mapper;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Product;
import ru.alex.nextpizzaapi.dto.product.ProductReadDto;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {
    @Override
    public ProductReadDto toDto(Product entity) {
        return new ProductReadDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl()
        );
    }
}
