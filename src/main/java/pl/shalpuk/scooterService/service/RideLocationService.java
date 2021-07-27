package pl.shalpuk.scooterService.service;

import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.exception.ServiceException;
import pl.shalpuk.scooterService.model.RideLocation;
import pl.shalpuk.scooterService.repository.RideLocationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class RideLocationService {

    private final RideLocationRepository rideLocationRepository;

    public RideLocationService(RideLocationRepository rideLocationRepository) {
        this.rideLocationRepository = rideLocationRepository;
    }

    public List<RideLocation> getRideLocationStatistic(LocalDateTime dateFrom, LocalDateTime dateTo) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (Objects.isNull(dateTo)) {
            dateTo = currentTime;
        }

        if (Objects.isNull(dateFrom)) {
            dateFrom = currentTime.minusDays(1);
        }

        if (dateTo.isBefore(dateFrom)) {
            throw new ServiceException(String.format("dateFrom [%s] value is more than dateTo [%s]", dateFrom, dateTo));
        }

        return rideLocationRepository.getAllByPositionTimeBetween(dateFrom, dateTo);
    }
}
