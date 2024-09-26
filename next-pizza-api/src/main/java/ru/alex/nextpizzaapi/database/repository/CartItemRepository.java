package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.CartItem;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query("""
        SELECT ci FROM CartItem ci
        LEFT JOIN FETCH ci.ingredients cii
        LEFT JOIN FETCH cii.ingredient
        WHERE ci.cart.id = :cartId
        """)
    List<CartItem> findCartItemsWithIngredients(Integer cartId);


    @Modifying
    @Query("UPDATE CartItem ci SET ci.quantity = :quantity WHERE ci.id = :id")
    void updateQuantity(Integer quantity, Integer id);
}
