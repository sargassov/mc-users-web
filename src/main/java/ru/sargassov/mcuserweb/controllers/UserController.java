package ru.sargassov.mcuserweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.sargassov.mcuserweb.exceptions.UserNotFoundException;
import ru.sargassov.mcuserweb.converters.UserConverter;
import ru.sargassov.mcuserweb.dto.UserDto;
import ru.sargassov.mcuserweb.entites.User;
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

    @GetMapping("/{id}")
    public UserDto getProductById(@PathVariable Long id) {
        User user = (User) userService.findById(id).orElseThrow(() -> new UserNotFoundException("User not found, id: " + id));
        return userConverter.entityToDto(user);
    }

    @PostMapping
    public UserDto saveNewUser(@RequestBody UserDto userDto) {
        userValidator.validate(userDto);
        User user = userConverter.dtoToEntity(userDto);
        user = userService.save(user);
        return userConverter.entityToDto(user);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userDto) {
        userValidator.validate(userDto);
        User user = userService.update(userDto);
        return userConverter.entityToDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
