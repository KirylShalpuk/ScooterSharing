package pl.shalpuk.scooterService.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.DefaultTariffs;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserRepository;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    public void createDefaultData() {
        Role adminRole = createDefaultAdminRole();
        Role defaultUserRole = createDefaultUserRole();
        createSuperAdminUser();
        createDefaultAdmin(adminRole);
        createDefaultUser(defaultUserRole);
        createScooters();
        createRegularTariff();
        createPremiumTariff();
        createDiscountTariff();
    }

    private Flyway configureFlyway() {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations(locations)
                .outOfOrder(true)
                .load();
    }

    private Role createDefaultAdminRole() {
        Role admin = new Role();
        admin.setName(DefaultRoles.ADMIN.getName());
        admin.setDescription(DefaultRoles.ADMIN.getDescription());
        admin.setActive(true);
        admin.setAdmin(true);
        return roleRepository.save(admin);
    }

    private Role createDefaultUserRole() {
        Role user = new Role();
        user.setName(DefaultRoles.USER.getName());
        user.setDescription(DefaultRoles.USER.getDescription());
        user.setActive(true);
        return roleRepository.save(user);
    }

    private User createSuperAdminUser() {
        User superAdmin = new User();
        superAdmin.setActive(true);
        superAdmin.setPhoneNumber("+48798743377");
        superAdmin.setEmail("super.admin@scooter.com");
        superAdmin.setPassword("$2a$10$0sBp8KSHQW/VuTjGJSnoO.nbRcr3mcfStc5BxA5s61A4Mt2vU6iq."); //1-8
        return userRepository.save(superAdmin);
    }

    private User createDefaultAdmin(Role adminRole) {
        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPhoneNumber("+48798743378");
        admin.setEmail("admin@scooter.com");
        admin.setPassword("$2a$10$0sBp8KSHQW/VuTjGJSnoO.nbRcr3mcfStc5BxA5s61A4Mt2vU6iq."); //1-8
        admin.setActive(true);
        admin.setRole(adminRole);
        return userRepository.save(admin);
    }

    private User createDefaultUser(Role userRole) {
        User user = new User();
        user.setFirstName("Kiryl");
        user.setLastName("Shalpuk");
        user.setPhoneNumber("+48798743379");
        user.setEmail("kirill.shelpuk@gmail.com");
        user.setPassword("$2a$10$0sBp8KSHQW/VuTjGJSnoO.nbRcr3mcfStc5BxA5s61A4Mt2vU6iq."); //1-8
        user.setActive(true);
        user.setRole(userRole);
        return userRepository.save(user);
    }

    private List<Scooter> createScooters() {
        List<Scooter> scooters = new ArrayList<>();

        int objectNumber = RandomUtils.nextInt(15, 25);
        for (int i = 0; i < objectNumber; i++) {
            Scooter scooter = new Scooter();
            scooter.setManufacturer("Xiaomi");
            scooter.setModel(String.format("Mi %s", RandomStringUtils.randomAlphabetic(3)));
            scooter.setSoftwareVersion("1.1." + RandomUtils.nextInt(1, 25));
            scooter.setLastService(Date.from(Instant.now().minus(Duration.ofDays(RandomUtils.nextInt(0, 90)))));
            scooter.setActive(RandomUtils.nextBoolean());
            scooter.setBatteryCharge(RandomUtils.nextInt(0, 100));
            scooter.setCharging(RandomUtils.nextBoolean());

            scooters.add(scooter);
        }

        return scooterRepository.saveAll(scooters);
    }

    private Tariff createRegularTariff() {
        Tariff regularTariff = new Tariff();
        regularTariff.setName(DefaultTariffs.REGULAR.getName());
        regularTariff.setDescription(DefaultTariffs.REGULAR.getDescription());
        regularTariff.setCosts(8);
        regularTariff.setActive(true);

        return tariffRepository.save(regularTariff);
    }

    private Tariff createPremiumTariff() {
        Tariff premiumTariff = new Tariff();
        premiumTariff.setName(DefaultTariffs.PREMIUM.getName());
        premiumTariff.setDescription(DefaultTariffs.PREMIUM.getDescription());
        premiumTariff.setCosts(12);
        premiumTariff.setActive(true);

        return tariffRepository.save(premiumTariff);
    }

    private Tariff createDiscountTariff() {
        Tariff discountTariff = new Tariff();
        discountTariff.setName(DefaultTariffs.DISCOUNT.getName());
        discountTariff.setDescription(DefaultTariffs.DISCOUNT.getDescription());
        discountTariff.setCosts(6);
        discountTariff.setActive(true);

        return tariffRepository.save(discountTariff);
    }

}
