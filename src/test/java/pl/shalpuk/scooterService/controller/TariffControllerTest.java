package pl.shalpuk.scooterService.controller;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TariffControllerTest extends AbstractJUnitControllerTest {

    @Test
    public void testGetTariffsById_200() throws Exception {
        UUID randomId = UUID.randomUUID();
        mockMvc.perform(get("/tariffs/{rideId}", randomId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllTariffsPage_200() throws Exception {
        mockMvc.perform(get("/tariffs"))
                .andExpect(status().isOk());
    }
}
