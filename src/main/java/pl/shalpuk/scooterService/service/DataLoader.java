package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserRepository;

import javax.sql.DataSource;
import javax.transaction.Transactional;

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
public class DataLoader {

    @Value("${spring.flyway.locations}")
    private String locations;

    private final Logger logger;
    private final DataSource dataSource;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ScooterRepository scooterRepository;
    private final TariffRepository tariffRepository;

    public DataLoader(Logger logger,
                      DataSource dataSource,
                      UserRepository userRepository,
                      RoleRepository roleRepository,
                      ScooterRepository scooterRepository,
                      TariffRepository tariffRepository) {
        this.logger = logger;
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.scooterRepository = scooterRepository;
        this.tariffRepository = tariffRepository;
    }

    @EventListener
    @Transactional
    public void uploadData(ContextRefreshedEvent event) {
        logger.info("Start flyway migrations...");
        Flyway flyway = configureFlyway();
        flyway.migrate();

        logger.info("Start uploading system default data...");
        if (userRepository.count() == 0) {
            createDefaultData();
            logger.info("Uploading system default data was finished successfully");
        } else {
            logger.info("Uploading system default data was skipped");
        }

    }

    private void createDefaultData() {
        Role adminRole = roleRepository.save(createDefaultAdminRole());
        Role defaultUserRole = roleRepository.save(createDefaultUserRole());

        userRepository.save(createSuperAdminUser());
        userRepository.save(createDefaultAdmin(adminRole));
        userRepository.save(createDefaultUser(defaultUserRole));

        scooterRepository.saveAll(createScooters());

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
