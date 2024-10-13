package ru.alex.nextpizzaapi.mapper.user;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.Role;
import ru.alex.nextpizzaapi.database.entity.User;
import ru.alex.nextpizzaapi.dto.user.UserRegisterDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class UserRegisterMapper implements Mapper<User, UserRegisterDto> {
    @Override
    public User toEntity(UserRegisterDto dto) {
        return User.builder()
                .email(dto.email())
                .password(dto.password())
                .fullName(dto.fullName())
                .role(Role.USER)
                .build();
    }
}
