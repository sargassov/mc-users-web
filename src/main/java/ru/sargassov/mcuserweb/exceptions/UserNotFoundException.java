package ru.sargassov.mcuserweb.exceptions;

public class UserNotFoundException extends RuntimeException { //Исключение. Юзер на найден
    public UserNotFoundException(String message) {
        super(message);
    }
}
