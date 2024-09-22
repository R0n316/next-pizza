package ru.alex.nextpizzaapi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.CartItem;
import ru.alex.nextpizzaapi.database.entity.Ingredient;
import ru.alex.nextpizzaapi.dto.cartItem.CartItemReadDto;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;
import ru.alex.nextpizzaapi.dto.productItem.ProductItemCartDto;

import java.util.List;

@Component
public class CartItemReadMapper implements Mapper<CartItem, CartItemReadDto>{
    private final ProductItemCartMapper productItemCartMapper;
    private final IngredientPreviewMapper ingredientPreviewMapper;

    @Autowired
    public CartItemReadMapper(ProductItemCartMapper productItemCartMapper,
                              IngredientPreviewMapper ingredientPreviewMapper) {
        this.productItemCartMapper = productItemCartMapper;
        this.ingredientPreviewMapper = ingredientPreviewMapper;
    }

    @Override
    public CartItemReadDto toDto(CartItem entity) {
        ProductItemCartDto productItem = productItemCartMapper.toDto(entity.getProductItem());
        List<IngredientPreviewDto> ingredients = entity.getIngredients()
                .stream()
                .map(cartItemIngredient -> {
                    Ingredient ingredient = cartItemIngredient.getIngredient();
                    return ingredientPreviewMapper.toDto(ingredient);
                })
                .toList();

        return new CartItemReadDto(
                entity.getId(),
                productItem,
                ingredients,
                entity.getQuantity(),
                entity.getCreatedAt()
        );
    }
}
