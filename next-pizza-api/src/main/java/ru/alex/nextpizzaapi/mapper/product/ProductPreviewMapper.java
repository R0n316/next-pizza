package ru.alex.nextpizzaapi.mapper.product;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Product;
import ru.alex.nextpizzaapi.dto.product.ProductPreviewDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class ProductPreviewMapper implements Mapper<Product, ProductPreviewDto> {
    @Override
    public ProductPreviewDto toDto(Product entity) {
        return new ProductPreviewDto(
                entity.getId(),
                entity.getName(),
                entity.getImageUrl()
        );
    }
}
