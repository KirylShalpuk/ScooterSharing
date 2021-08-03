package pl.shalpuk.scooterService.controller;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticControllerTest extends AbstractJUnitControllerTest {

    @Test
    public void testGetAllStatisticRidesList_200() throws Exception {
        mockMvc.perform(get("/statistics/rides/locations"))
                .andExpect(status().isOk());
    }
}
