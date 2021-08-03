package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.Scooter;

import java.time.LocalDateTime;

public class ScooterTestHelper {

    public static Scooter createScooter(int batteryCharge) {
        Scooter scooter = new Scooter();
        scooter.setManufacturer("Mercedes");
        scooter.setModel("AMG 1-01");
        scooter.setCharging(false);
        scooter.setBatteryCharge(batteryCharge);
        scooter.setActive(true);
        scooter.setLastService(LocalDateTime.now().minusDays(2));

        return scooter;
    }
}
