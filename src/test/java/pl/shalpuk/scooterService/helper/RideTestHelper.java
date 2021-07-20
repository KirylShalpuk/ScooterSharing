package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideStatus;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;

import java.time.LocalDateTime;

public class RideTestHelper {

    public static Ride createRide(User user, Scooter scooter, Tariff tariff) {
        Ride ride = new Ride();
        ride.setStartRideTime(LocalDateTime.now().minusHours(1));
        ride.setUser(user);
        ride.setScooter(scooter);
        ride.setTariff(tariff);
        ride.setRideStatus(RideStatus.STARTED);

        return ride;
    }
}
