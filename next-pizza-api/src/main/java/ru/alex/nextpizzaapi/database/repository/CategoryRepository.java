package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
            SELECT c
            FROM Category c
            LEFT JOIN FETCH c.products p
            ORDER BY c.id
            """)
    List<Category> findAllWithProducts();
}