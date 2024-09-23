package ru.alex.nextpizzaapi.dto.cart;

import ru.alex.nextpizzaapi.dto.cartItem.CartItemReadDto;

import java.util.List;

public record CartReadDto(
        Integer id,
        Integer userId,
        String token,
        Integer totalAmount,
        List<CartItemReadDto> items
) {
}