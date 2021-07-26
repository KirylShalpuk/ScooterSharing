package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.exception.ServiceException;
import pl.shalpuk.scooterService.model.PaymentStatus;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideStatus;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.RideRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RideService {

    private final Logger logger;
    private final RideRepository rideRepository;
    private final UserService userService;
    private final ScooterService scooterService;
    private final TariffService tariffService;

    public RideService(Logger logger,
                       RideRepository rideRepository,
                       UserService userService,
                       ScooterService scooterService,
                       TariffService tariffService) {
        this.logger = logger;
        this.rideRepository = rideRepository;
        this.userService = userService;
        this.scooterService = scooterService;
        this.tariffService = tariffService;
    }

    public Ride createRide(UUID userId, UUID scooterId, UUID tariffId, Ride request) {
        User user = userService.getUserById(userId);
        Scooter scooter = scooterService.getScooterById(scooterId);
        Tariff tariff = tariffService.getTariffById(tariffId);

        if (scooter.getBatteryCharge() < 10) {
            scooterService.deactivateScooter(scooter);
            throw new ServiceException(String.format("Scooter with id = %s has not enough " +
                    "battery charge to start the ride", scooterId));
        }

        request.setStartRideTime(LocalDateTime.now());
        request.setRideStatus(RideStatus.STARTED);
        request.setUser(user);
        request.setScooter(scooter);
        request.setTariff(tariff);

        request = rideRepository.save(request);
        logger.info(String.format("Ride with id = %s for user id = %s " +
                "and scooter id = %s was created successfully", request.getId(), userId, scooterId));

        return request;
    }

    public Ride finishRide(UUID rideId) {
        Ride ride = getRideById(rideId);
        ride.setEndRideTime(LocalDateTime.now());
        ride.setRideStatus(RideStatus.FINISHED);
        ride.setPaymentStatus(PaymentStatus.PROCESSING);

        ride = rideRepository.save(ride);
        logger.info(String.format("Ride with id = %s was finished successfully", rideId));

        return ride;
    }

    public Ride complainAboutRide(UUID rideId) {
        Ride ride = getRideById(rideId);

        if (!ride.getRideStatus().equals(RideStatus.FINISHED)) {
            throw new ServiceException(String.format("Ride with id = %s have not finished yet", rideId));
        }

        ride.setRideStatus(RideStatus.SERVICE);

        ride = rideRepository.save(ride);
        logger.info(String.format("Ride with id = %s was marked as SERVICE successfully", rideId));

        return ride;
    }

    public Ride getRideById(UUID rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ride with id is = %s is not found", rideId)));
    }

    public Page<Ride> getAllRidesPage(PageRequest pageRequest, String search) {
        if (search.isBlank()) {
            return rideRepository.getAllByUserEmailIgnoreCaseContaining(search, pageRequest);
        } else {
            return rideRepository.findAll(pageRequest);
        }
    }
}
