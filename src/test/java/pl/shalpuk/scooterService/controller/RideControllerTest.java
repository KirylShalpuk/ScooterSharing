package pl.shalpuk.scooterService.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pl.shalpuk.scooterService.dto.RideDto;
import pl.shalpuk.scooterService.dto.ShortScooterDto;
import pl.shalpuk.scooterService.dto.ShortTariffDto;
import pl.shalpuk.scooterService.dto.ShortUserDto;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RideControllerTest extends AbstractJUnitControllerTest {

    @Test
    public void testCreateRide_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        RideDto rideRequest = new RideDto();
        rideRequest.setUser(new ShortUserDto(randomId));
        rideRequest.setTariff(new ShortTariffDto(randomId));
        rideRequest.setScooter(new ShortScooterDto(randomId));

        String requestBody = objectMapper.writeValueAsString(rideRequest);
        mockMvc.perform(post("/rides")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateRide_400() throws Exception {
        UUID randomId = UUID.randomUUID();
        RideDto rideRequest = new RideDto();
        rideRequest.setUser(new ShortUserDto(randomId));
        rideRequest.setTariff(new ShortTariffDto(randomId));

        String requestBody = objectMapper.writeValueAsString(rideRequest);
        mockMvc.perform(post("/rides")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFinishRide_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(put("/rides/{rideId}/finish", randomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testComplainRide_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(put("/rides/{rideId}/complain", randomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRideById_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(get("/rides/{rideId}", randomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllRidesPage_200() throws Exception {
        mockMvc.perform(get("/rides"))
                .andExpect(status().isOk());
    }

}
