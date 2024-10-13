package ru.alex.nextpizzaapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alex.nextpizzaapi.database.repository.UserRepository;
import ru.alex.nextpizzaapi.dto.user.UserRegisterDto;
import ru.alex.nextpizzaapi.mapper.user.UserMapper;
import ru.alex.nextpizzaapi.mapper.user.UserRegisterMapper;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRegisterMapper userRegisterMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       UserRegisterMapper userRegisterMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRegisterMapper = userRegisterMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + email));
    }

    public void register(UserRegisterDto user) {
        userRepository.save(userRegisterMapper.toEntity(user));
    }
}
