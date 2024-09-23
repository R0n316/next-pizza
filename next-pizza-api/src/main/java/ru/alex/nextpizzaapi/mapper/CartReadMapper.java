package ru.alex.nextpizzaapi.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Cart;
import ru.alex.nextpizzaapi.database.entity.User;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.dto.cartItem.CartItemReadDto;

import java.util.List;
import java.util.Optional;

@Component
public class CartReadMapper implements Mapper<Cart, CartReadDto> {
    private final CartItemReadMapper cartItemReadMapper;

    @Autowired
    public CartReadMapper(CartItemReadMapper cartItemReadMapper) {
        this.cartItemReadMapper = cartItemReadMapper;
    }

    @Override
    public CartReadDto toDto(Cart entity) {
        Integer userId = Optional.ofNullable(entity.getUser())
                .map(User::getId)
                .orElse(null);
        List<CartItemReadDto> items = entity.getCartItems()
                .stream()
                .map(cartItemReadMapper::toDto)
                .toList();

        return new CartReadDto(
                entity.getId(),
                userId,
                entity.getToken(),
                entity.getTotalAmount(),
                items
        );
    }
}
