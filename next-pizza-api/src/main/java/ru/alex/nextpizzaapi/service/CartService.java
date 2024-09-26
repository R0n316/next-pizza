package ru.alex.nextpizzaapi.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.entity.Cart;
import ru.alex.nextpizzaapi.database.entity.CartItem;
import ru.alex.nextpizzaapi.database.repository.CartItemRepository;
import ru.alex.nextpizzaapi.database.repository.CartRepository;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.exception.CartItemNotFoundException;
import ru.alex.nextpizzaapi.exception.CartNotFoundException;
import ru.alex.nextpizzaapi.exception.CartTokenNotFoundException;
import ru.alex.nextpizzaapi.mapper.CartReadMapper;
import ru.alex.nextpizzaapi.utils.CartUtils;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class CartService {
    private static final String CART_TOKEN = "cartToken";

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartReadMapper cartReadMapper;

    @Autowired
    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       CartReadMapper cartReadMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartReadMapper = cartReadMapper;
    }


    public CartReadDto getCart(HttpServletRequest request) {
        return getCartToken(request.getCookies())
                .flatMap(cookie -> cartRepository.findByToken(cookie.getValue()))
                .map(cart -> {
                    cartItemRepository.findCartItemsWithIngredients(cart.getId());
                    return cartReadMapper.toDto(cart);
                })
                .orElse(new CartReadDto(null, null, null, 0, List.of()));
        // TODO если корзины нет, то создавать её
    }

    @Transactional
    public CartReadDto updateItemQuantity(Integer quantity, Integer itemId, HttpServletRequest request) {
        Optional<Cookie> cartToken = getCartToken(request.getCookies());

        return cartToken.map(token -> {
            Optional<Cart> cartOptional = cartRepository.findByToken(token.getValue());
            if(cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                Optional<CartItem> cartItemOptional = cartItemRepository.findById(itemId);
                CartItem cartItem = cartItemOptional
                        .orElseThrow(() -> new CartItemNotFoundException("No CartItem found with id: " + itemId));

                cartItemRepository.updateQuantity(quantity, cartItem.getQuantity());
                cartItem.setQuantity(quantity);


                cartItemRepository.findCartItemsWithIngredients(cart.getId());
                Integer updatedTotalAmount = CartUtils.countTotalAmount(cart.getCartItems());

                cartRepository.updateTotalAmount(updatedTotalAmount, cart.getId());
                cartRepository.flush();
                return cartReadMapper.toDto(cart);
            } else {
                throw new CartNotFoundException();
            }
        }).orElseThrow(CartTokenNotFoundException::new);
    }

    @Transactional
    public CartReadDto deleteItem(Integer itemId, HttpServletRequest request) {
        Optional<Cookie> cartToken = getCartToken(request.getCookies());

        return cartToken.map(token -> {
            Optional<Cart> cartOptional = cartRepository.findByToken(token.getValue());
            if(cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                Optional<CartItem> cartItemOptional = cartItemRepository.findById(itemId);
                CartItem cartItem = cartItemOptional
                        .orElseThrow(() -> new CartItemNotFoundException("No CartItem found with id: " + itemId));
                cartItemRepository.delete(cartItem.getId());
                cartItemRepository.findCartItemsWithIngredients(cart.getId());

                cartRepository.flush();
                return cartReadMapper.toDto(cart);
            } else {
                throw new CartNotFoundException();
            }

        }).orElseThrow(CartTokenNotFoundException::new);
    }

    private Optional<Cookie> getCartToken(Cookie[] cookies) {
        return Optional.ofNullable(cookies)
                .stream()
                .flatMap(Arrays::stream)
                .filter(c -> c.getName().equals(CART_TOKEN))
                .findFirst();
    }
}
