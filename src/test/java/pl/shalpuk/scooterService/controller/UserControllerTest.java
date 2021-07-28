package pl.shalpuk.scooterService.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pl.shalpuk.scooterService.dto.UserActivationDto;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.dto.UserRoleDto;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractJUnitControllerTest {


    @Test
    public void testCreateUser_200() throws Exception {
        UserDto userRequest = new UserDto();
        userRequest.setEmail("email@mail.com");
        userRequest.setPassword("123456");
        userRequest.setPhoneNumber("+123456789");

        String requestBody = objectMapper.writeValueAsString(userRequest);
        mockMvc.perform(post("/users")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser_400() throws Exception {
        UserDto userRequest = new UserDto();

        String requestBody = objectMapper.writeValueAsString(userRequest);
        mockMvc.perform(post("/users")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUser_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        UserDto userRequest = new UserDto();
        userRequest.setEmail("email@mail.com");
        userRequest.setPassword("123456");
        userRequest.setPhoneNumber("+123456789");

        String requestBody = objectMapper.writeValueAsString(userRequest);
        mockMvc.perform(put("/users/{userId}", randomId)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUser_400() throws Exception {
        UUID randomId = UUID.randomUUID();
        UserDto userRequest = new UserDto();

        String requestBody = objectMapper.writeValueAsString(userRequest);
        mockMvc.perform(put("/users/{userId}", randomId)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testActivateUser_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        UserActivationDto activationDto = new UserActivationDto();

        String requestBody = objectMapper.writeValueAsString(activationDto);
        mockMvc.perform(put("/users/{userId}/activation", randomId)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeactivateUser_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(put("/users/{userId}/deactivation", randomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateUserRole_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        UserRoleDto userRoleDto = new UserRoleDto();

        String requestBody = objectMapper.writeValueAsString(userRoleDto);
        mockMvc.perform(put("/users/{userId}/updateRole", randomId)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(get("/users/{userId}", randomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllUsersPage_200() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void tesDeleteUserById_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(delete("/users/{userId}", randomId))
                .andExpect(status().isOk());
    }

}
