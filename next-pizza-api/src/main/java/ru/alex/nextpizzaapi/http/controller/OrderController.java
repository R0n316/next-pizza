package ru.alex.nextpizzaapi.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.nextpizzaapi.dto.order.OrderCreateDto;
import ru.alex.nextpizzaapi.dto.payment.PaymentDto;
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
    public ResponseEntity<String> createOrder(@RequestBody OrderCreateDto orderCreateDto,
                                                  HttpServletRequest request) {
        return new ResponseEntity<>(orderService.create(orderCreateDto, request), OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> payForOrder(@PathVariable("id") Integer id,
                                                  @RequestBody PaymentDto paymentDto,
                                                  HttpServletRequest request) {
        orderService.payForOrder(paymentDto, id, request);
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> cancelOrder(@PathVariable("id") Integer id, HttpServletRequest request) {
        orderService.cancelOrder(id, request);
        return new ResponseEntity<>(OK);
    }
}
