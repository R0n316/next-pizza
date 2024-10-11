package ru.alex.nextpizzaapi.mapper.order;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Order;
import ru.alex.nextpizzaapi.dto.order.OrderPaymentDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class OrderPaymentMapper implements Mapper<Order, OrderPaymentDto> {
    @Override
    public OrderPaymentDto toDto(Order entity) {
        return new OrderPaymentDto(
                entity.getId(),
                entity.getTotalAmount(),
                entity.getOrderStatus().toString()
        );
    }
}
