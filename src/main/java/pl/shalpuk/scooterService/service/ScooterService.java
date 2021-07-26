package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.repository.ScooterRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ScooterService {

    private final Logger logger;
    private final ScooterRepository scooterRepository;

    public ScooterService(Logger logger, ScooterRepository scooterRepository) {
        this.logger = logger;
        this.scooterRepository = scooterRepository;
    }

    public Scooter createScooter(Scooter request) {
        Scooter scooter = scooterRepository.save(request);
        logger.info(String.format("Scooter with id = %s was created successfully", scooter.getId()));
        return scooter;
    }

    public Scooter getScooterById(UUID scooterId) {
        return scooterRepository.findById(scooterId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Scooter with id = %s is not found", scooterId)));
    }

    @Transactional
    public Scooter updateScooter(UUID scooterId, ScooterDto request) {
        Scooter scooter = getScooterById(scooterId);
        BeanUtils.copyProperties(request, scooter, "id", "version");
        if (scooter.getBatteryCharge() < 10) {
            deactivateScooter(scooter);
        }
        scooter = scooterRepository.save(scooter);
        logger.info(String.format("Scooter with id = %s was updated successfully", scooterId));
        return scooter;
    }

    void deactivateScooter(Scooter scooter) {
        scooter.setActive(false);
        scooterRepository.save(scooter);
        logger.info(String.format("Scooter with id = %s was deactivated because of battery charge is low", scooter.getId()));
    }

    public void deleteScooterById(UUID scooterId) {
        Scooter scooter = getScooterById(scooterId);
        scooterRepository.delete(scooter);
        logger.info(String.format("Scooter with id = %s was deleted successfully", scooterId));
    }

    public Scooter getAllScootersPage(PageRequest pageRequest, String search) {
        return null;
    }
}
