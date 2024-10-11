package ru.alex.nextpizzaapi.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.alex.nextpizzaapi.database.entity.Cart;
import ru.alex.nextpizzaapi.database.entity.Order;
import ru.alex.nextpizzaapi.database.entity.OrderStatus;
import ru.alex.nextpizzaapi.database.repository.CartItemRepository;
import ru.alex.nextpizzaapi.database.repository.CartRepository;
import ru.alex.nextpizzaapi.database.repository.OrderRepository;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.dto.email.EmailDto;
import ru.alex.nextpizzaapi.dto.order.OrderCreateDto;
import ru.alex.nextpizzaapi.dto.order.OrderPaymentDto;
import ru.alex.nextpizzaapi.dto.payment.PaymentDto;
import ru.alex.nextpizzaapi.exception.CartNotFoundException;
import ru.alex.nextpizzaapi.exception.CartTokenNotFoundException;
import ru.alex.nextpizzaapi.mapper.cart.CartReadMapper;
import ru.alex.nextpizzaapi.mapper.order.OrderCreateMapper;
import ru.alex.nextpizzaapi.mapper.order.OrderPaymentMapper;

import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static ru.alex.nextpizzaapi.utils.CartUtils.getCartToken;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final EmailService emailService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderCreateMapper orderCreateMapper;
    private final CartReadMapper cartReadMapper;
    private final OrderPaymentMapper orderPaymentMapper;

    @Autowired
    public OrderService(EmailService emailService,
                        OrderRepository orderRepository,
                        OrderCreateMapper orderCreateMapper,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        CartReadMapper cartReadMapper,
                        OrderPaymentMapper orderPaymentMapper) {
        this.emailService = emailService;
        this.orderRepository = orderRepository;
        this.orderCreateMapper = orderCreateMapper;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartReadMapper = cartReadMapper;
        this.orderPaymentMapper = orderPaymentMapper;
    }

    public OrderPaymentDto get(String orderSecret, HttpServletRequest request) {
        return orderPaymentMapper.toDto(getOrder(orderSecret, request));
    }

    @Transactional
    public String create(OrderCreateDto orderCreateDto, HttpServletRequest request) {
        // Получение токена из cookies
        String token = getCartToken(request.getCookies())
                .map(Cookie::getValue)
                .orElseThrow(CartTokenNotFoundException::new);
        // Получение корзины по токену
        Cart cart = cartRepository.findByToken(token)
                .orElseThrow(CartNotFoundException::new);
        cartItemRepository.findCartItemsWithIngredients(cart.getId());
        CartReadDto cartReadDto = cartReadMapper.toDto(cart);
        // Ошибка, если корзина пустая
        if(cartReadDto.items().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        // Создание заказа
        Order order = orderCreateMapper.toEntity(orderCreateDto);
        order.setToken(token);
        String secret = UUID.randomUUID().toString();
        order.setSecret(secret);
        order.setItems(cartReadDto.items());
        order.setOrderStatus(OrderStatus.PENDING);
        // очистка корзины со всеми связанными с ней сущностями
        cartItemRepository.deleteByCart(cart.getId());
        cart.setTotalAmount(0);
        // сохранение заказа в бд
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        String paymentLink = "/orders/" + secret;

        emailService.sendEmail(
                new EmailDto(
                        orderCreateDto.email(),
                        "Next pizza / Оплатите заказ #" + savedOrder.getId(),
                        "confirm-order-email.html",
                        Map.of("orderId", savedOrder.getId(),
                                "totalAmount", savedOrder.getTotalAmount(),
                                "paymentLink", "http://localhost:3000" + paymentLink)
                        )
                );
        return paymentLink;
    }

    @Transactional
    public void payForOrder(PaymentDto paymentDto, String orderSecret, HttpServletRequest request) {
        Order order = getOrder(orderSecret, request);
        order.setOrderStatus(OrderStatus.SUCCEEDED);
        order.setPaymentId(paymentDto.cardNumber());
        orderRepository.save(order);

        emailService.sendEmail(
                new EmailDto(
                        order.getEmail(),
                        "Next pizza / Заказ #" + order.getId() + " оплачен",
                        "succeeded-payment-email.html",
                        Map.of(
                                "orderId", order.getId(),
                                "totalAmount", order.getTotalAmount(),
                                "items", order.getItems()
                        )
                )
        );
    }

    @Transactional
    public void cancelOrder(String orderSecret, HttpServletRequest request) {
        Order order = getOrder(orderSecret, request);
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setPaymentId(null);
        orderRepository.save(order);

        emailService.sendEmail(
                new EmailDto(
                        order.getEmail(),
                        "Next pizza / Заказ #" + order.getId() + " отменён",
                        "canceled-order-email.html",
                        Map.of("orderId", order.getId())
                )
        );
    }

    private Order getOrder(String orderSecret, HttpServletRequest request) {
        String token = getCartToken(request.getCookies())
                .map(Cookie::getValue)
                .orElseThrow(CartTokenNotFoundException::new);
        Order order = orderRepository.findBySecret(orderSecret)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        if(!order.getToken().equals(token)) {
            throw new ResponseStatusException(FORBIDDEN);
        }
        return order;
    }
}
