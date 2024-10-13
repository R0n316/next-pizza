package ru.alex.nextpizzaapi.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alex.nextpizzaapi.dto.auth.AuthResponse;
import ru.alex.nextpizzaapi.dto.user.UserRegisterDto;
import ru.alex.nextpizzaapi.service.JwtService;
import ru.alex.nextpizzaapi.service.UserService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
     private final JwtService jwtService;
     private final UserService userService;

    @Autowired
    public AuthController(JwtService jwtService,
                          UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> performRegistration(@RequestBody UserRegisterDto user) {
        userService.register(user);
        String token = jwtService.generateToken(user.email());
        return new ResponseEntity<>(new AuthResponse(token), CREATED);
    }
}
