package pl.shalpuk.scooterService.controller;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleControllerTest extends AbstractJUnitControllerTest {

    @Test
    public void testGetAllRolesPage_200() throws Exception {
        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk());
    }

}
