package ru.alex.nextpizzaapi.mapper.user;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.User;
import ru.alex.nextpizzaapi.dto.user.UserDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

import java.util.Collections;

@Component
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getFullName(),
                Collections.singletonList(entity.getRole())
        );
    }
}
