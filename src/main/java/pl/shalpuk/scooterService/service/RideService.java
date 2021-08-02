package pl.shalpuk.scooterService.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pl.shalpuk.scooterService.dto.RideSpecificationDto;
import pl.shalpuk.scooterService.exception.ServiceException;
import pl.shalpuk.scooterService.model.PaymentStatus;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideStatus;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.ScooterStatus;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.RideRepository;
import pl.shalpuk.scooterService.util.LogUtil;
import pl.shalpuk.scooterService.util.RideSpecification;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;
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

    @Transactional
    public Ride createRide(UUID userId, UUID scooterId, UUID tariffId, Ride request) {
        User user = userService.getUserById(userId);
        Scooter scooter = scooterService.getScooterById(scooterId);
        Tariff tariff = tariffService.getTariffById(tariffId);


        if (scooter.getBatteryCharge() < 10) {
            scooterService.deactivateScooter(scooter);
            throw new ServiceException(String.format("Scooter with id = %s has not enough " +
                    "battery charge to start the ride", scooterId));
        }

        if (!scooter.getScooterStatus().equals(ScooterStatus.READY)) {
            throw new ServiceException(String.format("Scooter with id = %s is not ready to start ride [%s]",
                    scooterId, scooter.getScooterStatus()));
        }

        scooter = scooterService.changeScooterStatus(scooter, ScooterStatus.TAKEN);

        request.setStartRideTime(LocalDateTime.now());
        request.setRideStatus(RideStatus.STARTED);
        request.setUser(user);
        request.setScooter(scooter);
        request.setTariff(tariff);

        request = rideRepository.save(request);
        LogUtil.logInfo(logger, String.format("Ride with id = %s for user id = %s " +
                "and scooter id = %s was created successfully", request.getId(), userId, scooterId));

        return request;
    }

    @Transactional
    public Ride finishRide(UUID rideId) {
        Ride ride = getRideById(rideId);
        ride.setEndRideTime(LocalDateTime.now());
        ride.setRideStatus(RideStatus.FINISHED);
        ride.setPaymentStatus(PaymentStatus.PROCESSING);

        scooterService.changeScooterStatus(ride.getScooter(), ScooterStatus.READY);

        ride = rideRepository.save(ride);
        LogUtil.logInfo(logger, String.format("Ride with id = %s was finished successfully", rideId));

        return ride;
    }

    @Transactional
    public Ride complainAboutRide(UUID rideId) {
        Ride ride = getRideById(rideId);

        scooterService.changeScooterStatus(ride.getScooter(), ScooterStatus.SERVICE);

        if (!ride.getRideStatus().equals(RideStatus.FINISHED)) {
            throw new ServiceException(String.format("Ride with id = %s have not finished yet", rideId));
        }

        ride.setRideStatus(RideStatus.SERVICE);

        ride = rideRepository.save(ride);
        LogUtil.logInfo(logger, String.format("Ride with id = %s was marked as SERVICE successfully", rideId));

        return ride;
    }

    public Ride getRideById(UUID rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ride with id is = %s is not found", rideId)));
    }

    public Page<Ride> getAllRidesPage(PageRequest pageRequest, String search, RideSpecificationDto specificationDto) {
        if (showEmptyPage(specificationDto)) {
            return Page.empty();
        }

        RideSpecification rideSpecification = new RideSpecification(specificationDto);

        if (!StringUtils.isEmpty(search)) {
            return rideRepository.getAllByUserEmailIgnoreCaseContaining(search, rideSpecification, pageRequest);
        } else {
            return rideRepository.findAll(rideSpecification, pageRequest);
        }
    }

    private boolean showEmptyPage(RideSpecificationDto rideSpecificationDto) {
        return CollectionUtils.isEmpty(rideSpecificationDto.getLocationAddress())
                || CollectionUtils.isEmpty(rideSpecificationDto.getUserEmails());
    }

    public RideSpecificationDto getRideFilterProperties() {
        Set<String> userEmail = rideRepository.getAllUserEmail();
        Set<String> locations = rideRepository.getAllLocationAddresses();

        RideSpecificationDto rideSpecificationDto = new RideSpecificationDto();
        rideSpecificationDto.setUserEmails(userEmail);
        rideSpecificationDto.setLocationAddress(locations);

        return rideSpecificationDto;
    }
}
