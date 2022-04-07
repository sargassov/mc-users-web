package ru.sargassov.mcuserweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse { //обертка для токена
    private String token;
}
