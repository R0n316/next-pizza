package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.UserRepository;
import ru.alex.nextpizzaapi.dto.auth.AuthResponse;
import ru.alex.nextpizzaapi.dto.user.UserLoginDto;
import ru.alex.nextpizzaapi.dto.user.UserRegisterDto;
import ru.alex.nextpizzaapi.mapper.user.UserRegisterMapper;

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
        userRepository.save(userRegisterMapper.toEntity(user));
        String token = jwtService.generateToken(user.email());
        return new AuthResponse(token);
    }

    public AuthResponse login(UserLoginDto user) {
        UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                user.email(),
                user.password()
        );
        authManager.authenticate(authInputToken);
        String token = jwtService.generateToken(user.email());
        return new AuthResponse(token);
    }
}
