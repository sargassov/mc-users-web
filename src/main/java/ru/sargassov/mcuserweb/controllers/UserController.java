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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;
    private final UserValidator userValidator;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllusers().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> saveNewUser(@RequestBody UserDto userDto) {
        try {
            userValidator.validate(userDto);
        } catch (ValidationException e){
            StringBuilder message = new StringBuilder("");
            for(String s : e.getErrorFieldsMessages()) message.append(s + ",");
            return new ResponseEntity<>(new Notice(HttpStatus.BAD_REQUEST.value(), "Saving user did not pass the verification. " + message.toString()), HttpStatus.BAD_REQUEST);
        }
        User user = userConverter.dtoToEntity(userDto);
        user = userService.save(user);
        return new ResponseEntity<>(new Notice(HttpStatus.OK.value(),"New user " + user.getUsername() + " saved"), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        if(!userService.update(userDto)){
            return new ResponseEntity<>(new Notice(HttpStatus.NOT_FOUND.value(), "Editable user not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Notice(HttpStatus.OK.value(),"All updates " + userDto.getLogin() + " were saved"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if(id != 1){
            System.out.println("all good delete");
            userService.deleteById(id);
            return new ResponseEntity<>(new Notice(HttpStatus.OK.value(),"User was deleted"), HttpStatus.OK);
        }
        System.out.println("mistake delete");
        return new ResponseEntity<>(new Notice(HttpStatus.BAD_REQUEST.value(), "Brian Eno can't be deteted"), HttpStatus.BAD_REQUEST);
    }
}
