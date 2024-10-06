package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.CartItem;

import java.util.List;
import java.util.Optional;

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
    @Query("UPDATE CartItem ci SET ci.quantity = :quantity WHERE ci.id = :cartItemId")
    void updateQuantity(Integer quantity, Integer cartItemId);


    @Query("""
        SELECT ci
        FROM CartItem ci
        LEFT JOIN FETCH CartItemIngredient cii ON ci.id = cii.cartItem.id
        WHERE ci.cart.id = :cartId AND ci.productItem.id = :productItemId
        GROUP BY ci.id
        HAVING COUNT(DISTINCT cii.ingredient.id) = :ingredientsCount
        AND SUM(CASE WHEN cii.ingredient.id IN :ingredients THEN 1 ELSE 0 END) = COUNT(DISTINCT cii.ingredient.id)
        """)
    Optional<CartItem> findByCartAndIngredients(
            Integer cartId,
            Integer productItemId,
            List<Integer> ingredients,
            Integer ingredientsCount
    );

    @Modifying
    @Query(value = """
        INSERT INTO cart_item(quantity ,product_item_id, cart_id)
        VALUES
        (1, :productItemId, :cartId)
        """, nativeQuery = true)
    void create(Integer productItemId, Integer cartId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId ORDER BY ci.createdAt DESC")
    List<CartItem> findByCart(Integer cartId);


    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    @Modifying
    void deleteByCart(Integer cartId);
}
