package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.UserRepository;

import javax.sql.DataSource;
import javax.transaction.Transactional;

@Component
public class DataLoader {

    @Value("${spring.flyway.locations}")
    private String locations;

    private final Logger logger;
    private final DataSource dataSource;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DataLoader(Logger logger,
                      DataSource dataSource,
                      UserRepository userRepository,
                      RoleRepository roleRepository) {
        this.logger = logger;
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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


}
