package pl.shalpuk.scooterService.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.shalpuk.scooterService.dto.AuthDto;
import pl.shalpuk.scooterService.dto.JwtTokenDto;
import pl.shalpuk.scooterService.service.security.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class AuthControllerTest extends AbstractJUnitControllerTest {

    @Mock
    private AuthService authService;

    @Test
    public void testLogin_200() throws Exception {
        AuthDto loginRequest = new AuthDto();
        loginRequest.setLogin("admin@scooter.com");
        loginRequest.setPassword("12345678");

        String requestBody = objectMapper.writeValueAsString(loginRequest);
        mockMvc.perform(post("/auth/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testLogout_200() throws Exception {
        String token = "this is token";
        JwtTokenDto logoutRequest = new JwtTokenDto("this is token");

        Mockito.doNothing().when(authService).logoutUser(any());

//        Mockito.when(jwtTokenRepository.findByTokenAndActiveIsTrue(token)).thenReturn(Optional.of(new JwtToken()));
        Mockito.doNothing().when(authService).logoutUser(any());

        String requestBody = objectMapper.writeValueAsString(logoutRequest);
        mockMvc.perform(put("/auth/logout")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
