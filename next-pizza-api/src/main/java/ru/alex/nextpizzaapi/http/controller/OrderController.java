package ru.alex.nextpizzaapi.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.nextpizzaapi.dto.order.OrderCreateDto;
import ru.alex.nextpizzaapi.service.OrderService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createOrder(@RequestBody OrderCreateDto orderCreateDto,
                                                  HttpServletRequest request) {
        orderService.create(orderCreateDto, request);
        return new ResponseEntity<>(OK);
    }
}
