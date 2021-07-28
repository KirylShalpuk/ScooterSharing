package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.dto.ScooterSpecificationDto;
import pl.shalpuk.scooterService.helper.ScooterTestHelper;
import pl.shalpuk.scooterService.model.Scooter;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

class ScooterServiceTest extends AbstractJunitTest {

    @Test
    public void testCreateScooter_ScooterNotExist_Created() {
        Assertions.assertEquals(20, scooterRepository.count());
        scooterService.createScooter(ScooterTestHelper.createScooter(100));
        Assertions.assertEquals(21, scooterRepository.count());
    }

    @Test
    public void testDeleteScooterById_ScooterExists_Deleted() {
        Scooter savedScooter = scooterRepository.save(ScooterTestHelper.createScooter(100));

        Assertions.assertEquals(21, scooterRepository.count());
        scooterService.deleteScooterById(savedScooter.getId());
        Assertions.assertEquals(20, scooterRepository.count());
        Assertions.assertFalse(scooterRepository.existsById(savedScooter.getId()));
    }

    @Test
    public void testDeleteScooterById_ScooterNotExist_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> scooterService.deleteScooterById(UUID.randomUUID()));
    }

    @Test
    public void testGetScooterById_ScooterExists_Success() {
        Scooter savedScooter = scooterRepository.save(ScooterTestHelper.createScooter(100));

        Scooter scooterFromDb = scooterService.getScooterById(savedScooter.getId());
        Assertions.assertNotNull(scooterFromDb);
    }

    @Test
    public void testGetScooterById_ScooterNotExists_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> scooterService.getScooterById(UUID.randomUUID()));
    }

    @Test
    public void testUpdateScooter_ScooterExists_Updated() {
        Scooter savedScooter = scooterRepository.save(ScooterTestHelper.createScooter(100));

        ScooterDto request = new ScooterDto();
        request.setManufacturer(savedScooter.getManufacturer());
        request.setModel(savedScooter.getModel());
        request.setLastService(Date.from(Instant.now()));
        request.setSoftwareVersion(savedScooter.getSoftwareVersion());
        request.setBatteryCharge(5);
        request.setCharging(false);

        Scooter updatedScooter = scooterService.updateScooter(savedScooter.getId(), request);
        Assertions.assertFalse(updatedScooter.isActive());
        Assertions.assertEquals(5, updatedScooter.getBatteryCharge());
    }

    @Test
    public void testUpdateScooter_ScooterExists_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> scooterService.updateScooter(UUID.randomUUID(), new ScooterDto()));
    }

    @Test
    public void testGetAllScootersPage_FilterNotNull_ReturnAllScootersOnPage() {
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.ASC, "model");
        ScooterSpecificationDto specificationDto = scooterService.getScooterFilterProperties();
        specificationDto.setActive(false);
        Page<Scooter> scooterPage = scooterService.getAllScootersPage(pageRequest, specificationDto);

        Assertions.assertEquals(20, scooterRepository.count());
        Assertions.assertEquals(20, scooterPage.getTotalElements());
    }

    @Test
    public void testGetAllScootersPage_FilterEmpty_ReturnEmptyPage() {
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.ASC, "model");
        Page<Scooter> scooterPage = scooterService.getAllScootersPage(pageRequest, new ScooterSpecificationDto());

        Assertions.assertEquals(20, scooterRepository.count());
        Assertions.assertEquals(0, scooterPage.getTotalElements());
    }
}