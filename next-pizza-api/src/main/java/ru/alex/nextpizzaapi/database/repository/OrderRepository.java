package ru.alex.nextpizzaapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex.nextpizzaapi.database.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}