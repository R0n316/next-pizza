package ru.alex.nextpizzaapi.dto.order;

import ru.alex.nextpizzaapi.database.entity.OrderStatus;

public record OrderCreateDto(
        Float totalAmount,
        OrderStatus status,
        String fullName,
        String email,
        String phone,
        String comment,
        String address
) {
}