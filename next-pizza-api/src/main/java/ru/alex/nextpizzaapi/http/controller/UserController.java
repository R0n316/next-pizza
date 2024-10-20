package ru.alex.nextpizzaapi.http.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.nextpizzaapi.dto.user.UserCreateEditDto;
import ru.alex.nextpizzaapi.dto.user.UserReadDto;
import ru.alex.nextpizzaapi.service.UserService;
import ru.alex.nextpizzaapi.utils.JwtUtils;

import static org.springframework.http.HttpStatus.OK;

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
        return new ResponseEntity<>(user, OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserReadDto> update(@PathVariable("id") Integer id,
                                              @RequestBody UserCreateEditDto user) {
        return new ResponseEntity<>(userService.update(id, user), OK);
    }
}
