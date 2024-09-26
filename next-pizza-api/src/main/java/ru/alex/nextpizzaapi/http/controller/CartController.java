package ru.alex.nextpizzaapi.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.dto.cartItem.CartItemUpdateDto;
import ru.alex.nextpizzaapi.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public CartReadDto getCart(HttpServletRequest request) {
        return cartService.getCart(request);
    }

    @PatchMapping("/{id}")
    public CartReadDto updateCartItemQuantity(@PathVariable("id") Integer itemId,
                                              @RequestBody CartItemUpdateDto data,
                                              HttpServletRequest request) {
        return cartService.updateItemQuantity(data.quantity(), itemId, request);
    }
}