package ru.alex.nextpizzaapi.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.entity.*;
import ru.alex.nextpizzaapi.database.repository.*;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.dto.cartItem.CartItemCreateDto;
import ru.alex.nextpizzaapi.exception.CartItemNotFoundException;
import ru.alex.nextpizzaapi.exception.CartNotFoundException;
import ru.alex.nextpizzaapi.exception.CartTokenNotFoundException;
import ru.alex.nextpizzaapi.mapper.CartReadMapper;
import ru.alex.nextpizzaapi.utils.CartUtils;

import java.util.*;

import static ru.alex.nextpizzaapi.utils.CartUtils.*;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final IngredientRepository ingredientRepository;
    private final ProductItemRepository productItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemIngredientRepository cartItemIngredientRepository;
    private final CartReadMapper cartReadMapper;

    @Autowired
    public CartService(IngredientRepository ingredientRepository,
                       ProductItemRepository productItemRepository,
                       CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       CartItemIngredientRepository cartItemIngredientRepository,
                       CartReadMapper cartReadMapper) {
        this.ingredientRepository = ingredientRepository;
        this.productItemRepository = productItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartItemIngredientRepository = cartItemIngredientRepository;
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
    public CartReadDto addItem(CartItemCreateDto cartItemDto, HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> cartToken = getCartToken(request.getCookies());
        String token;
        if (cartToken.isPresent()) {
            token = cartToken.get().getValue();
        } else {
            token = CartUtils.generateCartToken();
        }
        Cart cart = findOrCreateCart(token, response);
        Optional<CartItem> findCartItem = cartItemRepository.findByCartAndIngredients(
                cart.getId(),
                cartItemDto.ingredientIds(),
                cartItemDto.ingredientIds().size()
        );
        if (findCartItem.isPresent()) {
            CartItem cartItem = findCartItem.get();
            cartItemRepository.updateQuantity(cartItemDto.quantity(), cartItem.getId());
            cartItem.setQuantity(cartItemDto.quantity());
            cart.getCartItems().add(cartItem);

        } else {
            ProductItem productItem = productItemRepository.findById(cartItemDto.productItemId())
                    .orElseThrow(() -> new RuntimeException("Product item not found"));
            List<CartItemIngredient> ingredients = ingredientRepository
                    .findAllByIds(cartItemDto.ingredientIds())
                    .stream()
                    .map(ingredient -> CartItemIngredient
                            .builder()
                            .ingredient(ingredient)
                            .build())
                    .toList();
            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .productItem(productItem)
                    .quantity(1)
                    .ingredients(ingredients)
                    .build();
            cart.getCartItems().add(newCartItem);
            CartItem savedCartItem = cartItemRepository.save(newCartItem);
            for(CartItemIngredient ingredient: ingredients) {
                ingredient.setCartItem(savedCartItem);
            }
            cartItemIngredientRepository.saveAll(ingredients);

        }
        Cart updatedCart = cartRepository.findById(cart.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Integer updatedTotalAmount = countTotalAmount(updatedCart.getCartItems());
        cartRepository.updateTotalAmount(updatedTotalAmount, updatedCart.getId());

        return cartReadMapper.toDto(updatedCart);
    }

    @Transactional
    public CartReadDto updateItemQuantity(Integer quantity, Integer itemId, HttpServletRequest request) {
        Optional<Cookie> cartToken = getCartToken(request.getCookies());

        return cartToken.map(token -> {
            Optional<Cart> cartOptional = cartRepository.findByToken(token.getValue());
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                Optional<CartItem> cartItemOptional = cartItemRepository.findById(itemId);
                CartItem cartItem = cartItemOptional
                        .orElseThrow(() -> new CartItemNotFoundException("No CartItem found with id: " + itemId));

                cartItemRepository.updateQuantity(quantity, cartItem.getQuantity());
                cartItem.setQuantity(quantity);


                Integer updatedTotalAmount = countTotalAmount(cart.getCartItems());

                cartItemRepository.findCartItemsWithIngredients(cart.getId());
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
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
                Optional<CartItem> cartItemOptional = cartItemRepository.findById(itemId);
                CartItem cartItem = cartItemOptional
                        .orElseThrow(() -> new CartItemNotFoundException("No CartItem found with id: " + itemId));

                cartItemRepository.findCartItemsWithIngredients(cart.getId());
                Integer updatedTotalAmount = countTotalAmount(cart.getCartItems());
                cartRepository.updateTotalAmount(updatedTotalAmount, cart.getId());

                cart.getCartItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
                cartItemRepository.findCartItemsWithIngredients(cart.getId());

                cartRepository.flush();
                return cartReadMapper.toDto(cart);
            } else {
                throw new CartNotFoundException();
            }

        }).orElseThrow(CartTokenNotFoundException::new);
    }

    private Cart findOrCreateCart(String token, HttpServletResponse response) {
        return cartRepository.findByToken(token).orElseGet(() -> {
            Cart cart = Cart.builder()
                    .totalAmount(0)
                    .cartItems(new ArrayList<>())
                    .build();
            cart.setToken(token);
            Cookie cookie = new Cookie("cartToken", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return cartRepository.save(cart);
        });
    }
}
