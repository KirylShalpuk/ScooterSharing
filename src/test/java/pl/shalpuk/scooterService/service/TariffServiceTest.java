package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.shalpuk.scooterService.model.Tariff;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

class TariffServiceTest extends AbstractJunitTest {

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

}