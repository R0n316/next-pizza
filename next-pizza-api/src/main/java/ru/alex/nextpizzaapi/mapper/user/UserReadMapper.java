package ru.alex.nextpizzaapi.mapper.user;

import org.springframework.stereotype.Component;
import ru.alex.nextpizzaapi.database.entity.User;
import ru.alex.nextpizzaapi.dto.user.UserReadDto;
import ru.alex.nextpizzaapi.mapper.Mapper;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto toDto(User entity) {
        return new UserReadDto(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail()
        );
    }
}
