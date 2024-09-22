package ru.alex.nextpizzaapi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.ProductItem;
import ru.alex.nextpizzaapi.dto.product.ProductPreviewDto;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemCartDto;

@Component
public class ProductItemCartMapper implements Mapper<ProductItem, ProductItemCartDto>{
    private final ProductPreviewMapper productPreviewMapper;

    @Autowired
    public ProductItemCartMapper(ProductPreviewMapper productPreviewMapper) {
        this.productPreviewMapper = productPreviewMapper;
    }

    @Override
    public ProductItemCartDto toDto(ProductItem entity) {
        ProductPreviewDto product = productPreviewMapper.toDto(entity.getProduct());

        return new ProductItemCartDto(
                entity.getId(),
                entity.getPrice(),
                entity.getSize(),
                entity.getPizzaType(),
                product
        );
    }
}
