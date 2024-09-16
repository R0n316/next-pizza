package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.IngredientProduct;
import ru.alex.nextpizzaapi.dto.ingredient.IngredientPreviewDto;

import java.util.List;

@Repository
public interface IngredientProductRepository extends JpaRepository<IngredientProduct, Integer> {
    @Query("""
            SELECT i.name AS name
            FROM IngredientProduct ip
            JOIN FETCH Ingredient i ON ip.ingredient.id = i.id
            WHERE ip.product.id = :productId
            """)
    List<IngredientPreviewDto> findAllByProduct(Integer productId, Pageable pageable);
}
