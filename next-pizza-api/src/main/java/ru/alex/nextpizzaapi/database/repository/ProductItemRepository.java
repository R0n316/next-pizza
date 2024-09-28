package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.ProductItem;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Integer> {
}
