package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.PaymentStatus;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideLocation;
import pl.shalpuk.scooterService.model.RideStatus;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.ScooterStatus;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class RideHelper {

    public static Ride generateStartedRide(Scooter scooter, User user, Tariff tariff, List<Location> locations) {
        RideLocation startPoint = new RideLocation();
        startPoint.setLocation(scooter.getCurrentLocation());
        startPoint.setPositionTime(LocalDateTime.now().minusHours(1));

        Ride ride = new Ride();
        ride.setRideStatus(RideStatus.STARTED);
        ride.setStartRideTime(LocalDateTime.now().minusHours(1));
        ride.setUser(user);
        ride.setScooter(scooter);
        ride.setTariff(tariff);
        ride.addRideLocation(startPoint);

        locations.stream()
                .limit(10)
                .sorted(Comparator.comparing(Location::getStreet))
                .forEach(location -> RideLocationHelper.generateRideLocation(locations, ride, location));

        scooter.setScooterStatus(ScooterStatus.TAKEN);

        return ride;
    }

    public static Ride generateFinishedRide(Scooter scooter, User user, Tariff tariff, List<Location> locations) {
        RideLocation startPoint = new RideLocation();
        startPoint.setLocation(scooter.getCurrentLocation());

        Ride ride = new Ride();
        ride.setRideStatus(RideStatus.FINISHED);
        ride.setStartRideTime(LocalDateTime.now().minusHours(1));
        ride.setEndRideTime(LocalDateTime.now());
        ride.setUser(user);
        ride.setScooter(scooter);
        ride.setTariff(tariff);
        ride.addRideLocation(startPoint);
        ride.setPaymentStatus(PaymentStatus.PROCESSING);

        locations.stream()
                .limit(5)
                .sorted(Comparator.comparing(Location::getStreet))
                .forEach(location -> RideLocationHelper.generateRideLocation(locations, ride, location));

        return ride;
    }


}
