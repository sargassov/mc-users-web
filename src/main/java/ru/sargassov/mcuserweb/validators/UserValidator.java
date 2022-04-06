package ru.sargassov.mcuserweb.validators;

import org.springframework.stereotype.Component;
import ru.sargassov.mcuserweb.dto.UserDto;
import ru.sargassov.mcuserweb.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserValidator {
    private String name;
    private String lastname;
    private String birthDate;
    private String login;
    private String password;
    private String info;
    private String address;

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
        if (userDto.getInfo().isBlank()) {
            errors.add("Поле \"О себе\" должно быть заполнено");
        }
        if (userDto.getLogin().isBlank()) {
            errors.add("Логин не может быть пустым");
        }
        if (userDto.getPassword().isBlank()) {
            errors.add("Пароль не может быть пустым");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
