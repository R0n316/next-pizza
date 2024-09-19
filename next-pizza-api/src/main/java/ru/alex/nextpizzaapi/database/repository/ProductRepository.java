package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.name ILIKE :name")
    List<Product> findAllLikeName(String name, Pageable pageable);


    @Query("""
            SELECT p
            FROM Product p
            LEFT JOIN FETCH p.productItems
        """)
    List<Product> findAllWithProductItems();

    @Query("""
        SELECT p
        FROM Product p
        LEFT JOIN FETCH p.ingredientProducts ip
        LEFT JOIN FETCH ip.ingredient
        """)
    List<Product> findAllWithIngredients();
}