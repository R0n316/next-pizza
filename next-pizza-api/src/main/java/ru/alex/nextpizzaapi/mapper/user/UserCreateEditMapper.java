package ru.alex.nextpizzaapi.mapper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.alex.nextpizzaapi.database.entity.Role;
import ru.alex.nextpizzaapi.database.entity.User;
import ru.alex.nextpizzaapi.dto.user.UserCreateEditDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

import java.util.Optional;

@Component
public class UserCreateEditMapper implements Mapper<User, UserCreateEditDto> {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCreateEditMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User toEntity(UserCreateEditDto dto) {
        User user = new User();
        Optional.ofNullable(dto.password())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);
        user.setEmail(dto.email());
        user.setFullName(dto.fullName());
        user.setRole(Role.USER);
        return user;
    }
}
