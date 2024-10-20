package ru.alex.nextpizzaapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.nextpizzaapi.database.entity.User;
import ru.alex.nextpizzaapi.database.repository.UserRepository;
import ru.alex.nextpizzaapi.dto.user.UserCreateEditDto;
import ru.alex.nextpizzaapi.dto.user.UserReadDto;
import ru.alex.nextpizzaapi.exception.UserNotFoundException;
import ru.alex.nextpizzaapi.mapper.user.UserCreateEditMapper;
import ru.alex.nextpizzaapi.mapper.user.UserMapper;
import ru.alex.nextpizzaapi.mapper.user.UserReadMapper;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       UserReadMapper userReadMapper,
                       UserCreateEditMapper userCreateEditMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userReadMapper = userReadMapper;
        this.userCreateEditMapper = userCreateEditMapper;
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
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Transactional
    public UserReadDto update(Integer userId, UserCreateEditDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        User updatedUser = userCreateEditMapper.toEntity(userDto);
        updatedUser.setId(user.getId());
        return userReadMapper.toDto(userRepository.save(updatedUser));
    }

    private String getEmailFromJwt(String jwt) {
        DecodedJWT decodedJwt = JWT.decode(jwt);
        return decodedJwt.getClaim("email").asString();
    }
}
