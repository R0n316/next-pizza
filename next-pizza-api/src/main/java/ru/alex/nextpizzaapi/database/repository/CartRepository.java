package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("""
            SELECT c FROM Cart c
            LEFT JOIN FETCH c.cartItems ci
            LEFT JOIN FETCH ci.productItem pi
            LEFT JOIN FETCH pi.product p
            WHERE c.token = :token
            ORDER BY ci.createdAt DESC
            """)
    Optional<Cart> findByToken(String token);

    @Modifying
    @Query("UPDATE Cart c SET c.totalAmount = :totalAmount WHERE c.id = :id")
    void updateTotalAmount(Integer totalAmount, Integer id);
}