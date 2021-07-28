package pl.shalpuk.scooterService.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import pl.shalpuk.scooterService.dto.AuthDto;
import pl.shalpuk.scooterService.dto.JwtTokenDto;
import pl.shalpuk.scooterService.model.JwtToken;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends AbstractJUnitControllerTest {

    @Test
    public void testLogin_200() throws Exception {
        AuthDto loginRequest = new AuthDto();
        loginRequest.setLogin("admin@scooter.com");
        loginRequest.setPassword("12345678");

        String requestBody = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogout_200() throws Exception {
        String token = "this is token";
        JwtTokenDto logoutRequest = new JwtTokenDto("this is token");

        Mockito.doNothing().when(authService).logoutUser(any());
        Mockito.when(jwtTokenRepository.findByTokenAndActiveIsTrue(anyString())).thenReturn(Optional.of(new JwtToken()));
//        Mockito.doNothing().when(authService).logoutUser(any(JwtTokenDto.class));

        String requestBody = objectMapper.writeValueAsString(logoutRequest);
        mockMvc.perform(put("/auth/logout")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
