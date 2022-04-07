package ru.sargassov.mcuserweb.validators;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.sargassov.mcuserweb.dto.UserDto;
import ru.sargassov.mcuserweb.exceptions.ValidationException;
import ru.sargassov.mcuserweb.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class UserValidator {
    private final UserService userService;


    public void validate(UserDto userDto) {
        List<String> errors = new ArrayList<>();
        if (userDto.getName().isBlank()) {
            errors.add("Имя пользователя не может быть пустым");
        }
        if (userDto.getAddress().isBlank()) {
            errors.add("Адрес не может быть пустым");
        }
        if (userDto.getBirthDate().isBlank()) {
            errors.add("День рождения должен быть заполнен");
        }
        if (userDto.getLastname().isBlank()) {
            errors.add("Фамилия должна быть заполнена");
        }
        if (userDto.getLogin().isBlank()) {
            errors.add("Логин не может быть пустым");
        }
        if (userService.isResident(userDto.getLogin())) {
            errors.add("Логин не может повторяться");
        }
        if (userDto.getPassword().isBlank()) {
            errors.add("Пароль не может быть пустым");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
