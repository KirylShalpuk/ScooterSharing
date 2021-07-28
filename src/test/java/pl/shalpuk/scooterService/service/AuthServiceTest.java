package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.shalpuk.scooterService.dto.AuthDto;
import pl.shalpuk.scooterService.dto.JwtTokenDto;
import pl.shalpuk.scooterService.model.JwtToken;
import pl.shalpuk.scooterService.model.User;

public class AuthServiceTest extends AbstractJunitTest {

    @Test
    public void testLogin_UserExists_Successfully() {
        User user = userRepository.findAll().stream().findFirst().orElse(null);

        AuthDto loginRequest = new AuthDto();
        loginRequest.setLogin(user.getEmail());
        loginRequest.setPassword("12345678");

        JwtToken tokenResponse = authService.loginUser(loginRequest);
        Assertions.assertFalse(tokenResponse.getToken().isEmpty());
    }

    @Test
    public void testLogout() {
        User user = userRepository.findAll().stream().findFirst().orElse(null);
        String token = "this is token";
        JwtToken jwtToken = new JwtToken();
        jwtToken.setActive(true);
        jwtToken.setToken(token);
        jwtToken.setUser(user);

        JwtTokenDto tokenDto = new JwtTokenDto(token);

        jwtToken = jwtTokenRepository.save(jwtToken);

        authService.logoutUser(tokenDto);

        JwtToken updateToken = jwtTokenRepository.getById(jwtToken.getId());
        Assertions.assertFalse(updateToken.isActive());
    }

}
