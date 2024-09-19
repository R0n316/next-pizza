package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.Ingredient;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    @Query("SELECT ip.ingredient FROM IngredientProduct ip WHERE ip.product.id = :productId")
    List<Ingredient> findAllByProduct(Integer productId);
}