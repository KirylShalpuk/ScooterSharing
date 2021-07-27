package pl.shalpuk.scooterService.helper;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Scooter;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ScooterHelper {

    public static List<Scooter> createScooters(List<Location> locations) {
        List<Scooter> scooters = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Scooter scooter = new Scooter();
            scooter.setManufacturer("Xiaomi");
            scooter.setModel(String.format("Mi %s", RandomStringUtils.randomAlphabetic(3)));
            scooter.setSoftwareVersion("1.1." + RandomUtils.nextInt(1, 25));
            scooter.setLastService(Date.from(Instant.now().minus(Duration.ofDays(RandomUtils.nextInt(0, 90)))));
            scooter.setActive(RandomUtils.nextBoolean());
            scooter.setBatteryCharge(RandomUtils.nextInt(0, 100));
            scooter.setCharging(RandomUtils.nextBoolean());
            scooter.setCurrentLocation(locations.get(RandomUtils.nextInt(0, 19)));

            scooters.add(scooter);
        }

        return scooters;
    }

}
