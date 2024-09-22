package ru.alex.nextpizzaapi.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.CartRepository;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.mapper.CartReadMapper;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CartService {
    private final Integer userId = 1;

    private final CartRepository cartRepository;
    private final CartReadMapper cartReadMapper;

    @Autowired
    public CartService(CartRepository cartRepository,
                       CartReadMapper cartReadMapper) {
        this.cartRepository = cartRepository;
        this.cartReadMapper = cartReadMapper;
    }

    public CartReadDto getCart(HttpServletRequest request) {
        Cookie[] cookies = {new Cookie("token", "11111")};
        if(cookies == null) {
            return null;
        }
        Optional<String> token = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .map(Cookie::getName);
        return token.flatMap(s -> cartRepository
                        .findByTokenOrUser(s, userId)
                        .map(cartReadMapper::toDto)
                )
                .orElse(null);
        // TODO если корзины нет, то создавать её
    }
}
