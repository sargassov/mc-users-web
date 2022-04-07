package ru.sargassov.mcuserweb.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sargassov.mcuserweb.dto.JwtRequest;
import ru.sargassov.mcuserweb.dto.JwtResponse;
import ru.sargassov.mcuserweb.dto.UserDto;
import ru.sargassov.mcuserweb.exceptions.Notice;
import ru.sargassov.mcuserweb.services.UserService;
import ru.sargassov.mcuserweb.utils.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth") //регистрационный контроллер либо отправляет токен на фронт, либо ошибку 401
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest JwtRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(JwtRequest.getUsername(), JwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new Notice(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(JwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
