package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.repository.LocationRepository;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserRepository;
import pl.shalpuk.scooterService.util.LogUtil;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import java.util.List;

import static pl.shalpuk.scooterService.helper.LocationHelper.preparationLocations;
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

    @Value("${spring.flyway.locations}")
    private String locations;

    private final Logger logger;
    private final DataSource dataSource;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ScooterRepository scooterRepository;
    private final TariffRepository tariffRepository;
    private final LocationRepository locationRepository;

    public DataLoader(Logger logger,
                      DataSource dataSource,
                      UserRepository userRepository,
                      RoleRepository roleRepository,
                      ScooterRepository scooterRepository,
                      TariffRepository tariffRepository,
                      LocationRepository locationRepository) {
        this.logger = logger;
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.scooterRepository = scooterRepository;
        this.tariffRepository = tariffRepository;
        this.locationRepository = locationRepository;
    }

    @EventListener
    @Transactional
    public void uploadData(ContextRefreshedEvent event) {

        LogUtil.logInfo(logger, "Start flyway migrations...");
        Flyway flyway = configureFlyway();
        flyway.migrate();

        LogUtil.logInfo(logger, "Start uploading system default data...");
        if (userRepository.count() == 0) {
            createDefaultData();
            LogUtil.logInfo(logger, "Uploading system default data was finished successfully");
        } else {
            LogUtil.logInfo(logger, "Uploading system default data was skipped");
        }

        LogUtil.logInfo(logger, "Preparation locations...");
    }

    private void createDefaultData() {
        Role adminRole = roleRepository.save(createDefaultAdminRole());
        Role defaultUserRole = roleRepository.save(createDefaultUserRole());

        List<Location> locations = locationRepository.saveAll(preparationLocations());

        userRepository.save(createSuperAdminUser());
        userRepository.save(createDefaultAdmin(adminRole));
        userRepository.save(createDefaultUser(defaultUserRole));

        scooterRepository.saveAll(createScooters(locations));

        tariffRepository.save(createRegularTariff());
        tariffRepository.save(createPremiumTariff());
        tariffRepository.save(createDiscountTariff());

    }

    private Flyway configureFlyway() {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(locations)
                .outOfOrder(true)
                .load();
    }
}
