package ru.alex.nextpizzaapi.http.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.nextpizzaapi.dto.auth.AuthResponse;
import ru.alex.nextpizzaapi.dto.user.UserLoginDto;
import ru.alex.nextpizzaapi.dto.user.UserCreateEditDto;
import ru.alex.nextpizzaapi.service.AuthService;
import ru.alex.nextpizzaapi.service.JwtService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthService authService,
                          JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> performRegistration(@RequestBody UserCreateEditDto user, HttpServletResponse response) {
        return new ResponseEntity<>(authService.register(user, response), CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> performLogin(@RequestBody UserLoginDto user, HttpServletResponse response) {
        return new ResponseEntity<>(authService.login(user, response), OK);
    }

    @PatchMapping("/refresh-token")
    public ResponseEntity<HttpStatus> refreshAccessToken(@RequestBody AuthResponse authResponse, HttpServletResponse response) {
        jwtService.validateTokenAndRetrieveClaim(authResponse.jwt(), response);
        return new ResponseEntity<>(OK);
    }
}
