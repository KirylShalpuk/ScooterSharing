package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.shalpuk.scooterService.exception.ServiceException;
import pl.shalpuk.scooterService.helper.RideTestHelper;
import pl.shalpuk.scooterService.helper.ScooterTestHelper;
import pl.shalpuk.scooterService.helper.UserTestHelper;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.DefaultTariffs;
import pl.shalpuk.scooterService.model.PaymentStatus;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideStatus;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

class RideServiceTest extends AbstractJunitTest {

    @Test
    void testCreateRide_AllEntitiesExist_Created() {
        Role viewer = roleService.getRoleByName(DefaultRoles.VIEWER.toString());
        User user = userRepository.save(UserTestHelper.createUser(viewer));
        Tariff tariff = getTariffByName(DefaultTariffs.REGULAR.getName());
        Scooter scooter = scooterRepository.findAll().stream()
                .filter(scooterFromList -> scooterFromList.isActive() && scooterFromList.getBatteryCharge() > 10)
                .findFirst().orElse(scooterRepository.save(ScooterTestHelper.createScooter(100)));

        Ride request = new Ride();
        request.setRideStatus(RideStatus.STARTED);

        Assertions.assertEquals(0, rideRepository.count());

        Ride savedRide = rideService.createRide(user.getId(), scooter.getId(), tariff.getId(), request);

        Assertions.assertEquals(RideStatus.STARTED, savedRide.getRideStatus());
        Assertions.assertEquals(LocalDateTime.now().getHour(), savedRide.getStartRideTime().getHour());
        Assertions.assertEquals(LocalDateTime.now().getMinute(), savedRide.getStartRideTime().getMinute());
        Assertions.assertEquals(user.getId(), savedRide.getUser().getId());
        Assertions.assertEquals(scooter.getId(), savedRide.getScooter().getId());
        Assertions.assertEquals(tariff.getId(), savedRide.getTariff().getId());
    }

    @Test
    void testCreateRide_UserNotExist_EntityNotFoundException() {
        Tariff tariff = getTariffByName(DefaultTariffs.REGULAR.getName());
        Scooter scooter = scooterRepository.findAll().stream()
                .filter(scooterFromList -> scooterFromList.isActive() && scooterFromList.getBatteryCharge() > 10)
                .findFirst().orElse(scooterRepository.save(ScooterTestHelper.createScooter(100)));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> rideService.createRide(UUID.randomUUID(), scooter.getId(), tariff.getId(), new Ride()));
    }

    @Test
    void testCreateRide_ScooterNotExist_EntityNotFoundException() {
        Role viewer = roleService.getRoleByName(DefaultRoles.VIEWER.toString());
        User user = userRepository.save(UserTestHelper.createUser(viewer));
        Tariff tariff = getTariffByName(DefaultTariffs.REGULAR.getName());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> rideService.createRide(user.getId(), UUID.randomUUID(), tariff.getId(), new Ride()));
    }

    @Test
    void testCreateRide_TariffNotExist_EntityNotFoundException() {
        Role viewer = roleService.getRoleByName(DefaultRoles.VIEWER.toString());
        User user = userRepository.save(UserTestHelper.createUser(viewer));
        Scooter scooter = scooterRepository.findAll().stream()
                .filter(scooterFromList -> scooterFromList.isActive() && scooterFromList.getBatteryCharge() > 10)
                .findFirst().orElse(scooterRepository.save(ScooterTestHelper.createScooter(100)));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> rideService.createRide(user.getId(), scooter.getId(), UUID.randomUUID(), new Ride()));
    }

    @Test
    void testCreateRide_ScooterNotCharged_ServiceException() {
        Role viewer = roleService.getRoleByName(DefaultRoles.VIEWER.toString());
        User user = userRepository.save(UserTestHelper.createUser(viewer));
        Tariff tariff = getTariffByName(DefaultTariffs.REGULAR.getName());
        Scooter scooter = scooterRepository.findAll().stream()
                .filter(scooterFromList -> scooterFromList.isActive() && scooterFromList.getBatteryCharge() < 10)
                .findFirst().orElse(scooterRepository.save(ScooterTestHelper.createScooter(5)));

        Assertions.assertThrows(ServiceException.class,
                () -> rideService.createRide(user.getId(), scooter.getId(), tariff.getId(), new Ride()));
    }

    @Test
    void testFinishRide_RideExists_Finished() {
        Role viewer = roleService.getRoleByName(DefaultRoles.VIEWER.toString());
        User user = userRepository.save(UserTestHelper.createUser(viewer));
        Tariff tariff = getTariffByName(DefaultTariffs.REGULAR.getName());
        Scooter scooter = scooterRepository.findAll().stream()
                .filter(scooterFromList -> scooterFromList.isActive() && scooterFromList.getBatteryCharge() < 10)
                .findFirst().orElse(scooterRepository.save(ScooterTestHelper.createScooter(30)));
        Ride ride = rideRepository.save(RideTestHelper.createRide(user, scooter, tariff));

        Ride finishedRide = rideService.finishRide(ride.getId());
        Assertions.assertEquals(RideStatus.FINISHED, finishedRide.getRideStatus());
        Assertions.assertEquals(PaymentStatus.PROCESSING, finishedRide.getPaymentStatus());
        Assertions.assertEquals(LocalDateTime.now().getHour(), finishedRide.getEndRideTime().getHour());
        Assertions.assertEquals(LocalDateTime.now().getMinute(), finishedRide.getEndRideTime().getMinute());
    }

    @Test
    void testFinishRide_RideNotExists_() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> rideService.finishRide(UUID.randomUUID()));
    }

    @Test
    void testGetRideById_RideExists_Success() {
        Role viewer = roleService.getRoleByName(DefaultRoles.VIEWER.toString());
        User user = userRepository.save(UserTestHelper.createUser(viewer));
        Tariff tariff = getTariffByName(DefaultTariffs.REGULAR.getName());
        Scooter scooter = scooterRepository.findAll().stream()
                .filter(scooterFromList -> scooterFromList.isActive() && scooterFromList.getBatteryCharge() < 10)
                .findFirst().orElse(scooterRepository.save(ScooterTestHelper.createScooter(30)));
        Ride ride = rideRepository.save(RideTestHelper.createRide(user, scooter, tariff));

        Ride rideFromDb = rideService.getRideById(ride.getId());
        Assertions.assertNotNull(rideFromDb);
    }

    @Test
    void testGetRideById_RideNotExists_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> rideService.getRideById(UUID.randomUUID()));
    }

    private Tariff getTariffByName(String roleName) {
        return tariffRepository.getTariffByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tariff with name = %s is not found", roleName)));
    }
}