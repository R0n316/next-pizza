package ru.alex.nextpizzaapi.utils;

import lombok.experimental.UtilityClass;
import ru.alex.nextpizzaapi.database.entity.CartItem;

import java.util.List;

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

}
