package pl.shalpuk.scooterService.service;

import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.LocationRepository;
import pl.shalpuk.scooterService.repository.RideRepository;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

import static pl.shalpuk.scooterService.helper.LocationHelper.preparationLocations;
import static pl.shalpuk.scooterService.helper.RideHelper.generateFinishedRide;
import static pl.shalpuk.scooterService.helper.RideHelper.generateStartedRide;
import static pl.shalpuk.scooterService.helper.RoleHelper.createDefaultAdminRole;
import static pl.shalpuk.scooterService.helper.RoleHelper.createDefaultUserRole;
import static pl.shalpuk.scooterService.helper.ScooterHelper.createScooters;
import static pl.shalpuk.scooterService.helper.TariffHelper.createDiscountTariff;
import static pl.shalpuk.scooterService.helper.TariffHelper.createPremiumTariff;
import static pl.shalpuk.scooterService.helper.TariffHelper.createRegularTariff;
import static pl.shalpuk.scooterService.helper.UserHelper.createDefaultAdmin;
import static pl.shalpuk.scooterService.helper.UserHelper.createDefaultUser;
import static pl.shalpuk.scooterService.helper.UserHelper.createSuperAdminUser;

@Component
@ComponentScan("pl.shalpuk")
public class DataLoader {

    @Value("${flyway.enable}")
    private boolean flywayAutoUpload;

    private final Logger logger;
    private final Flyway flyway;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ScooterRepository scooterRepository;
    private final TariffRepository tariffRepository;
    private final LocationRepository locationRepository;
    private final RideRepository rideRepository;

    public DataLoader(Logger logger,
                      Flyway flyway, UserRepository userRepository,
                      RoleRepository roleRepository,
                      ScooterRepository scooterRepository,
                      TariffRepository tariffRepository,
                      LocationRepository locationRepository, RideRepository rideRepository) {
        this.logger = logger;
        this.flyway = flyway;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.scooterRepository = scooterRepository;
        this.tariffRepository = tariffRepository;
        this.locationRepository = locationRepository;
        this.rideRepository = rideRepository;
    }

    @EventListener
    @Transactional
    public void uploadData(ContextRefreshedEvent event) {
        if (flywayAutoUpload) {
            logger.info("Start flyway migrations...");
            flyway.migrate();
            logger.info("Start uploading system default data...");
            if (userRepository.count() == 0) {
                createDefaultData();
                logger.info("Uploading system default data was finished successfully");
            } else {
                logger.info("Uploading system default data was skipped");
            }
        }
    }

    private void createDefaultData() {
        Role adminRole = roleRepository.save(createDefaultAdminRole());
        Role defaultUserRole = roleRepository.save(createDefaultUserRole());

        List<Location> locations = locationRepository.saveAll(preparationLocations());

        userRepository.save(createSuperAdminUser());
        userRepository.save(createDefaultAdmin(adminRole));
        User user = userRepository.save(createDefaultUser(defaultUserRole, locations.get(12)));

        List<Scooter> scooters = scooterRepository.saveAll(createScooters(locations));

        Tariff regularTariff = tariffRepository.save(createRegularTariff());
        tariffRepository.save(createPremiumTariff());
        tariffRepository.save(createDiscountTariff());

        rideRepository.save(generateStartedRide(scooters.get(0), user, regularTariff, locations));
        rideRepository.save(generateFinishedRide(scooters.get(1), user, regularTariff, locations));
    }


}
