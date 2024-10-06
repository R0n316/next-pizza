package ru.alex.nextpizzaapi.mapper.order;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Order;
import ru.alex.nextpizzaapi.dto.order.OrderCreateDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class OrderCreateMapper implements Mapper<Order, OrderCreateDto> {

    @Override
    public Order toEntity(OrderCreateDto dto) {

        return Order.builder()
                .totalAmount(dto.totalAmount())
                .orderStatus(dto.status())
                .fullName(dto.fullName())
                .email(dto.email())
                .phone(dto.phone())
                .comment(dto.comment())
                .address(dto.address())
                .build();
    }
}