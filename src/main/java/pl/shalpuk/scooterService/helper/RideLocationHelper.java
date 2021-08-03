package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideLocation;

import java.time.LocalDateTime;
import java.util.List;

public class RideLocationHelper {

    public static void generateRideLocation(List<Location> locations, Ride ride, Location location) {
        RideLocation rideLocation = new RideLocation();
        rideLocation.setLocation(location);
        rideLocation.setPositionTime(LocalDateTime.now().minusHours(1).plusMinutes(locations.indexOf(location)));
        ride.addRideLocation(rideLocation);
    }
}
