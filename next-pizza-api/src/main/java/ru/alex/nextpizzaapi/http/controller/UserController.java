package ru.alex.nextpizzaapi.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.nextpizzaapi.dto.user.UserReadDto;
import ru.alex.nextpizzaapi.service.UserService;
import ru.alex.nextpizzaapi.utils.JwtUtils;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserReadDto> findByEmail(HttpServletRequest request) {
        UserReadDto user = userService.findByEmail(JwtUtils.getEmailFromJwtInCookies(request.getCookies()));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
