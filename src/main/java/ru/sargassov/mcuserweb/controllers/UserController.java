package ru.sargassov.mcuserweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.mcuserweb.exceptions.Notice;
import ru.sargassov.mcuserweb.converters.UserConverter;
import ru.sargassov.mcuserweb.dto.UserDto;
import ru.sargassov.mcuserweb.entites.User;
import ru.sargassov.mcuserweb.exceptions.ValidationException;
import ru.sargassov.mcuserweb.services.UserService;
import ru.sargassov.mcuserweb.validators.UserValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users") // все запросы /users перехватываются здесь
@RequiredArgsConstructor
public class UserController { //главный контоллер
    private final UserService userService;
    private final UserConverter userConverter;
    private final UserValidator userValidator;

    @GetMapping
    public List<UserDto> getAllUsers() { //запрос всех юзеров
        return userService.getAllusers().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping //запрос на сохранение нового.
    public ResponseEntity<?> saveNewUser(@RequestBody UserDto userDto) {
        try {
            userValidator.validate(userDto); //если юзерДто не проходит валидацию на фронт оправится ошибка
        } catch (ValidationException e){
            StringBuilder message = new StringBuilder("");
            for(String s : e.getErrorFieldsMessages()) message.append(s + ",");
            return new ResponseEntity<>(new Notice(HttpStatus.BAD_REQUEST.value(), "Saving user did not pass the verification. " + message.toString()), HttpStatus.BAD_REQUEST);
        }
        User user = userConverter.dtoToEntity(userDto); //если валидация пройдена Дто преобразуется к сущности
        user = userService.save(user);
        return new ResponseEntity<>(new Notice(HttpStatus.OK.value(),"New user " + user.getUsername() + " saved"), HttpStatus.OK);
    }

    @PutMapping //изменение отдельных полей юзера. Вернет либо ошибку либо ОК. Основная его логика на UserService
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        if(!userService.update(userDto)){
            return new ResponseEntity<>(new Notice(HttpStatus.NOT_FOUND.value(), "Editable user not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Notice(HttpStatus.OK.value(),"All updates " + userDto.getLogin() + " were saved"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //Удаление юзера по ID
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if(id != 1){ //Запрет на удаление ROOTового юзера, так как удаление в БД каскадное
            userService.deleteById(id);
            return new ResponseEntity<>(new Notice(HttpStatus.OK.value(),"User was deleted"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Notice(HttpStatus.BAD_REQUEST.value(), "Brian Eno can't be deteted"), HttpStatus.BAD_REQUEST);
    }
}
