package ru.alex.nextpizzaapi.dto.cart;

import ru.alex.nextpizzaapi.dto.cartItem.CartItemReadDto;

import java.util.List;

public record CartReadDto(
        Integer id,
        Integer userId,
        String token,
        List<CartItemReadDto> items
) {
}