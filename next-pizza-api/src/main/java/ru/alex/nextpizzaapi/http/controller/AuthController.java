package ru.alex.nextpizzaapi.http.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.nextpizzaapi.dto.auth.AuthResponse;
import ru.alex.nextpizzaapi.dto.user.UserLoginDto;
import ru.alex.nextpizzaapi.dto.user.UserRegisterDto;
import ru.alex.nextpizzaapi.service.AuthService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> performRegistration(@RequestBody UserRegisterDto user) {
        return new ResponseEntity<>(authService.register(user), CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> performLogin(@RequestBody UserLoginDto user, HttpServletResponse response) {
        return new ResponseEntity<>(authService.login(user, response), OK);
    }
}
