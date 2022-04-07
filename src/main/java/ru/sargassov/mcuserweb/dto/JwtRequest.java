package ru.sargassov.mcuserweb.dto;

import lombok.Data;

@Data
public class JwtRequest { //обертка для запроса на выдачу токена
    private String username;
    private String password;
}
