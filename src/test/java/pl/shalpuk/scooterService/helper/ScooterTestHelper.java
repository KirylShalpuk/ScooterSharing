package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.Scooter;

import java.time.Instant;
import java.util.Date;

public class ScooterTestHelper {

    public static Scooter createScooter() {
        Scooter scooter = new Scooter();
        scooter.setManufacturer("Mercedes");
        scooter.setModel("AMG 1-01");
        scooter.setCharging(false);
        scooter.setBatteryCharge(100);
        scooter.setActive(true);
        scooter.setLastService(Date.from(Instant.now().minusSeconds(1000000)));

        return scooter;
    }
}
