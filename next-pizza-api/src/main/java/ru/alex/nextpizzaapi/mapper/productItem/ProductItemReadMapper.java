package ru.alex.nextpizzaapi.mapper.productItem;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.ProductItem;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemReadDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class ProductItemReadMapper implements Mapper<ProductItem, ProductItemReadDto> {
    @Override
    public ProductItemReadDto toDto(ProductItem entity) {
        return new ProductItemReadDto(
                entity.getId(),
                entity.getPrice(),
                entity.getSize(),
                entity.getPizzaType()
        );
    }
}
