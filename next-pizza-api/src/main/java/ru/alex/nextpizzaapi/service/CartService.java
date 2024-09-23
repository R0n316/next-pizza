package ru.alex.nextpizzaapi.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.CartItemRepository;
import ru.alex.nextpizzaapi.database.repository.CartRepository;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.dto.cartItem.CartItemReadDto;
import ru.alex.nextpizzaapi.mapper.CartReadMapper;

import java.util.*;

@Service
public class CartService {
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
        return Optional.ofNullable(request.getCookies())
                .stream()
                .flatMap(Arrays::stream)
                .filter(c -> c.getName().equals("cartToken"))
                .findFirst()
                .flatMap(cookie -> cartRepository.findByToken(cookie.getValue()))
                .map(cart -> {
                    cartItemRepository.findCartItemsWithIngredients(cart.getId());
                    CartReadDto cartReadDto = cartReadMapper.toDto(cart);
                    List<CartItemReadDto> sortedItems = new ArrayList<>(cartReadDto.items());
                    sortedItems.sort(Comparator.comparing(CartItemReadDto::createdAt).reversed());
                    return new CartReadDto(
                            cartReadDto.id(),
                            cartReadDto.userId(),
                            cartReadDto.token(),
                            cartReadDto.totalAmount(),
                            sortedItems
                    );
                })
                .orElse(new CartReadDto(null, null, null, 0, List.of()));

        // TODO если корзины нет, то создавать её
    }
}
