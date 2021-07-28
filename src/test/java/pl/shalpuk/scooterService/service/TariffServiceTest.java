package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.shalpuk.scooterService.model.Tariff;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

class TariffServiceTest extends AbstractIntegrationServiceTest {

    @Test
    public void testGetTariffById_TariffExists_Success() {
        Tariff tariff = new Tariff();
        tariffRepository.save(tariff);

        Tariff tariffFromDb = tariffService.getTariffById(tariff.getId());
        Assertions.assertNotNull(tariffFromDb);
    }

    @Test
    public void testGetTariffById_TariffNotExist_Success() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> tariffService.getTariffById(UUID.randomUUID()));
    }

    @Test
    public void testGetAllTariffsPage_ThreeTariffsExist_ReturnThreeTariffsOnPage() {
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.ASC, "name");
        Page<Tariff> tariffPage = tariffService.getAllTariffsPage(pageRequest);

        Assertions.assertEquals(3, tariffRepository.count());
        Assertions.assertEquals(3, tariffPage.getTotalElements());
    }

}