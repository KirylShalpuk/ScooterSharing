package pl.shalpuk.scooterService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.JwtTokenToDtoConverter;
import pl.shalpuk.scooterService.dto.AuthDto;
import pl.shalpuk.scooterService.dto.JwtTokenDto;
import pl.shalpuk.scooterService.model.JwtToken;
import pl.shalpuk.scooterService.service.security.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenToDtoConverter jwtTokenToDtoConverter;

    public AuthController(AuthService authService,
                          JwtTokenToDtoConverter jwtTokenToDtoConverter) {
        this.authService = authService;
        this.jwtTokenToDtoConverter = jwtTokenToDtoConverter;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto loginRequest) {
        JwtToken jwtToken = authService.loginUser(loginRequest);
        return ResponseEntity.ok(jwtTokenToDtoConverter.convertToDto(jwtToken));
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody JwtTokenDto logoutRequest) {
        authService.logoutUser(logoutRequest);
        return ResponseEntity.ok().build();
    }
}
