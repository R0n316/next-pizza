package ru.alex.nextpizzaapi.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.dto.cartItem.CartItemCreateDto;
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

    @DeleteMapping("/{id}")
    public CartReadDto deleteCartItem(@PathVariable("id") Integer id,
                                      HttpServletRequest request) {
        return cartService.deleteItem(id, request);
    }

    @PostMapping
    public CartReadDto addCartItem(@RequestBody CartItemCreateDto cartDto,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        return cartService.addItem(cartDto, request, response);
    }
}