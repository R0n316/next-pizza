package ru.alex.nextpizzaapi.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.entity.Cart;
import ru.alex.nextpizzaapi.database.entity.Order;
import ru.alex.nextpizzaapi.database.entity.OrderStatus;
import ru.alex.nextpizzaapi.database.repository.CartItemRepository;
import ru.alex.nextpizzaapi.database.repository.CartRepository;
import ru.alex.nextpizzaapi.database.repository.OrderRepository;
import ru.alex.nextpizzaapi.dto.cart.CartReadDto;
import ru.alex.nextpizzaapi.dto.email.EmailDto;
import ru.alex.nextpizzaapi.dto.order.OrderCreateDto;
import ru.alex.nextpizzaapi.exception.CartNotFoundException;
import ru.alex.nextpizzaapi.exception.CartTokenNotFoundException;
import ru.alex.nextpizzaapi.mapper.cart.CartReadMapper;
import ru.alex.nextpizzaapi.mapper.order.OrderCreateMapper;

import java.util.Map;

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
    @Autowired
    public OrderService(EmailService emailService,
                        OrderRepository orderRepository,
                        OrderCreateMapper orderCreateMapper,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        CartReadMapper cartReadMapper) {
        this.emailService = emailService;
        this.orderRepository = orderRepository;
        this.orderCreateMapper = orderCreateMapper;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartReadMapper = cartReadMapper;
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
        order.setItems(cartReadDto.items());
        order.setOrderStatus(OrderStatus.PENDING);
        // очистка корзины со всеми связанными с ней сущностями
        cartItemRepository.deleteByCart(cart.getId());
        cart.setTotalAmount(0);
        // сохранение заказа в бд
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        String paymentLink = "www.youtube.com";

        emailService.sendEmail(
                new EmailDto(
                        orderCreateDto.email(),
                        "Next pizza / Оплатите заказ #" + savedOrder.getId(),
                        "email.html",
                        Map.of("orderId", savedOrder.getId(),
                                "totalAmount", savedOrder.getTotalAmount(),
                                "paymentLink", paymentLink)
                        )
                );
        return "https://mail.google.com/";
        // TODO сделать создание ссылки оплаты
    }
}
