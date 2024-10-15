package ru.alex.nextpizzaapi.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.entity.User;
import ru.alex.nextpizzaapi.database.repository.UserRepository;
import ru.alex.nextpizzaapi.dto.auth.AuthResponse;
import ru.alex.nextpizzaapi.dto.user.UserLoginDto;
import ru.alex.nextpizzaapi.dto.user.UserRegisterDto;
import ru.alex.nextpizzaapi.exception.EmailAlreadyInUseException;
import ru.alex.nextpizzaapi.mapper.user.UserRegisterMapper;
import ru.alex.nextpizzaapi.utils.JwtUtils;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthService(UserRepository userRepository,
                       UserRegisterMapper userRegisterMapper,
                       JwtService jwtService,
                       AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.userRegisterMapper = userRegisterMapper;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public AuthResponse register(UserRegisterDto user) {
        userRepository.findByEmail(user.email())
                .ifPresent(it -> {
                    throw new EmailAlreadyInUseException();
                });
        User savedUser = userRepository.save(userRegisterMapper.toEntity(user));
        jwtService.generateRefreshToken(savedUser);
        String token = jwtService.generateAccessToken(user.email());
        return new AuthResponse(token);
    }

    public AuthResponse login(UserLoginDto userDto, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                userDto.email(),
                userDto.password()
        );
        authManager.authenticate(authInputToken);
        User user = userRepository.findByEmail(userDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("user with email " + userDto.email() + " not found"));
        jwtService.generateRefreshToken(user);
        String token = jwtService.generateAccessToken(userDto.email());
        JwtUtils.setJwtTokenToCookies(token, response);
        return new AuthResponse(token);
    }
}
