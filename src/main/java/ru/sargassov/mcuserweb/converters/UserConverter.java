package ru.sargassov.mcuserweb.converters;

import org.springframework.stereotype.Component;
import ru.sargassov.mcuserweb.dto.UserDto;
import ru.sargassov.mcuserweb.entites.User;

@Component
public class UserConverter {
    //конвертер преобразования сущности в дто и обратрно
    public User dtoToEntity(UserDto userDto){
        return new User(userDto);
    }

    public UserDto entityToDto (User user){
        return new UserDto(user);
    }
}
