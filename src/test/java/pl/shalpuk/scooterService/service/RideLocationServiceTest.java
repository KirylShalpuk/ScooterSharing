package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.shalpuk.scooterService.model.RideLocation;

import java.time.LocalDateTime;
import java.util.List;

class RideLocationServiceTest extends AbstractIntegrationServiceTest {

    @Test
    public void testGetRideLocationStatistic_() {
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();
        List<RideLocation> rideLocations = rideLocationService.getRideLocationStatistic(from, to);
        Assertions.assertFalse(rideLocations.isEmpty());
    }
}