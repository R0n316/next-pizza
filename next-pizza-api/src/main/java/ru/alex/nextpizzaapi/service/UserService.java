package ru.alex.nextpizzaapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.repository.UserRepository;
import ru.alex.nextpizzaapi.dto.user.UserReadDto;
import ru.alex.nextpizzaapi.mapper.user.UserMapper;
import ru.alex.nextpizzaapi.mapper.user.UserReadMapper;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserReadMapper userReadMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       UserReadMapper userReadMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userReadMapper = userReadMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + email));
    }

    public UserReadDto findByEmail(String jwt) {
        String email = getEmailFromJwt(jwt);
        return userRepository.findByEmail(email)
                .map(userReadMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    private String getEmailFromJwt(String jwt) {
        DecodedJWT decodedJwt = JWT.decode(jwt);
        return decodedJwt.getClaim("email").asString();
    }
}
