package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.dto.ScooterLocationStatisticDto;
import pl.shalpuk.scooterService.dto.ScooterSpecificationDto;
import pl.shalpuk.scooterService.dto.ScooterStatisticDto;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.ScooterStatus;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.ScooterStatisticRepository;
import pl.shalpuk.scooterService.util.AuthContext;
import pl.shalpuk.scooterService.util.LogUtil;
import pl.shalpuk.scooterService.util.ScooterSpecification;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScooterService {

    private final Logger logger;
    private final ScooterRepository scooterRepository;
    private final ScooterStatisticRepository scooterStatisticRepository;

    public ScooterService(Logger logger, ScooterRepository scooterRepository,
                          ScooterStatisticRepository scooterStatisticRepository) {
        this.logger = logger;
        this.scooterRepository = scooterRepository;
        this.scooterStatisticRepository = scooterStatisticRepository;
    }

    public Scooter createScooter(Scooter request) {
        Scooter scooter = scooterRepository.save(request);
        LogUtil.logInfo(logger, String.format("Scooter with id = %s was created successfully", scooter.getId()));
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
        LogUtil.logInfo(logger, String.format("Scooter with id = %s was updated successfully", scooterId));
        return scooter;
    }

    void deactivateScooter(Scooter scooter) {
        scooter.setActive(false);
        scooterRepository.save(scooter);
        LogUtil.logInfo(logger, String.format("Scooter with id = %s was " +
                "deactivated because of battery charge is low", scooter.getId()));
    }

    public void deleteScooterById(UUID scooterId) {
        Scooter scooter = getScooterById(scooterId);
        scooterRepository.delete(scooter);
        LogUtil.logInfo(logger, String.format("Scooter with id = %s was deleted successfully", scooterId));
    }

    public Page<Scooter> getAllScootersPage(PageRequest pageRequest, ScooterSpecificationDto scooterSpecificationDto) {
        if (showEmptyPage(scooterSpecificationDto)) {
            return Page.empty();
        }
        ScooterSpecification scooterSpecification = new ScooterSpecification(scooterSpecificationDto);
        return scooterRepository.findAll(scooterSpecification, pageRequest);
    }

    private boolean showEmptyPage(ScooterSpecificationDto scooterSpecificationDto) {
        return CollectionUtils.isEmpty(scooterSpecificationDto.getManufacturers())
                || CollectionUtils.isEmpty(scooterSpecificationDto.getModels())
                || CollectionUtils.isEmpty(scooterSpecificationDto.getLocationAddress())
                || scooterSpecificationDto.getBatteryChargeTo() == 0;
    }

    public ScooterSpecificationDto getScooterFilterProperties() {
        User user = AuthContext.getAuthContext();

        Set<String> manufacturers;
        Set<String> models;
        Set<String> locations;
        int batteryChargeFrom;
        int batteryChargeTo;
        boolean active;
        if (user.isAdmin()) {
            manufacturers = scooterRepository.getAllManufacturers();
            models = scooterRepository.getAllModels();
            locations = scooterRepository.getAllLocationAddress();
            batteryChargeFrom = scooterRepository.getMinBatteryCharge();
            batteryChargeTo = scooterRepository.getMaxBatteryCharge();
        } else {
            manufacturers = scooterRepository.getAllManufacturersActiveScooters();
            models = scooterRepository.getAllModelsActiveScooters();
            locations = scooterRepository.getAllLocationAddressActiveScooters();
            batteryChargeFrom = scooterRepository.getMinBatteryChargeActiveScooters();
            batteryChargeTo = scooterRepository.getMaxBatteryChargeActiveScooters();
        }
        active = scooterRepository.existsByActiveTrue();

        ScooterSpecificationDto specificationDto = new ScooterSpecificationDto();
        specificationDto.setManufacturers(manufacturers);
        specificationDto.setModels(models);
        specificationDto.setLocationAddress(locations);
        specificationDto.setBatteryChargeFrom(batteryChargeFrom);
        specificationDto.setBatteryChargeTo(batteryChargeTo);
        specificationDto.setActive(active);

        return specificationDto;
    }

    public ScooterLocationStatisticDto getAllFreeActiveScooters(ScooterLocationStatisticDto statisticDto) {
        List<Object[]> countHours = scooterStatisticRepository.getAllScooterStatisticForLastMonth(
                statisticDto.getStreet(), statisticDto.getBuilding(), LocalDate.now().minusMonths(1));
        List<ScooterStatisticDto> scooterStatisticDtos = countHours.stream().map(objectArray -> {
            ScooterStatisticDto scooterStatisticDto = new ScooterStatisticDto();
            scooterStatisticDto.setCountAVG((double) objectArray[0]);
            scooterStatisticDto.setTime((LocalTime) objectArray[1]);
            return scooterStatisticDto;
        }).collect(Collectors.toList());

        statisticDto.setScooterStatisticDtos(scooterStatisticDtos);

        return statisticDto;
    }

    public Scooter changeScooterStatus(Scooter scooter, ScooterStatus scooterStatus) {
        scooter.setScooterStatus(scooterStatus);
        return scooterRepository.save(scooter);
    }
}
