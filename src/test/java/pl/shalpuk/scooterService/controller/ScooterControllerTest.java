package pl.shalpuk.scooterService.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import pl.shalpuk.scooterService.dto.LocationDto;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.dto.ScooterSpecificationDto;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ScooterControllerTest extends AbstractJUnitControllerTest {


    @Test
    public void testGetScooterById_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(get("/scooters/{scooterId}", randomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllScootersPage_200() throws Exception {
        ScooterSpecificationDto specificationDto = new ScooterSpecificationDto();
        String filterRequest = objectMapper.writeValueAsString(specificationDto);
        mockMvc.perform(get("/scooters")
                .content(filterRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteScooterById_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(delete("/scooters/{scooterId}", randomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetScooterFilterProperties() throws Exception {
        mockMvc.perform(get("/scooters/filter")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateScooter_200() throws Exception {
        ScooterDto scooter = new ScooterDto();
        scooter.setManufacturer("Audi");
        scooter.setModel("i100");
        scooter.setActive(true);
        scooter.setBatteryCharge(50);
        scooter.setCurrentLocation(new LocationDto());

        String requestBody = objectMapper.writeValueAsString(scooter);
        mockMvc.perform(post("/scooters")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateScooter_400() throws Exception {
        ScooterDto scooter = new ScooterDto();

        String requestBody = objectMapper.writeValueAsString(scooter);
        mockMvc.perform(post("/scooters")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateScooter_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        ScooterDto scooter = new ScooterDto();
        scooter.setManufacturer("Audi");
        scooter.setModel("i100");
        scooter.setActive(true);
        scooter.setBatteryCharge(50);
        scooter.setCurrentLocation(new LocationDto());

        String requestBody = objectMapper.writeValueAsString(scooter);
        mockMvc.perform(put("/scooters/{randomId}", randomId)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateScooter_400() throws Exception {
        UUID randomId = UUID.randomUUID();
        ScooterDto scooter = new ScooterDto();

        String requestBody = objectMapper.writeValueAsString(scooter);
        mockMvc.perform(put("/scooters/{randomId}", randomId)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
