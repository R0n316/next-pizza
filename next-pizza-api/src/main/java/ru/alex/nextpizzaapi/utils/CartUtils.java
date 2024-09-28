package ru.alex.nextpizzaapi.utils;

import jakarta.servlet.http.Cookie;
import lombok.experimental.UtilityClass;
import ru.alex.nextpizzaapi.database.entity.CartItem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class CartUtils {
    public static Integer countTotalAmount(List<CartItem> cartItems) {
        int totalAmount = 0;
        for (CartItem cartItem : cartItems) {
            int productPrice = cartItem.getProductItem().getPrice();
            int quantity = cartItem.getQuantity();
            int ingredientsPrice = cartItem.getIngredients().stream()
                    .mapToInt(ingredient -> ingredient.getIngredient().getPrice())
                    .sum();
            totalAmount += (productPrice + ingredientsPrice) * quantity;
        }
        return totalAmount;
    }

    public static Optional<Cookie> getCartToken(Cookie[] cookies) {
        return Optional.ofNullable(cookies)
                .stream()
                .flatMap(Arrays::stream)
                .filter(c -> c.getName().equals("cartToken"))
                .findFirst();
    }

    public static String generateCartToken() {
        return UUID.randomUUID().toString();
    }
}
