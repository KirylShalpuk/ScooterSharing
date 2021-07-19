package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.shalpuk.scooterService.helper.UserTestHelper;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.CardRepository;
import pl.shalpuk.scooterService.repository.PaymentInformationRepository;
import pl.shalpuk.scooterService.repository.RideRepository;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserRepository;

import static pl.shalpuk.scooterService.helper.RoleHelper.createDefaultAdminRole;
import static pl.shalpuk.scooterService.helper.RoleHelper.createDefaultUserRole;
import static pl.shalpuk.scooterService.helper.ScooterHelper.createScooters;
import static pl.shalpuk.scooterService.helper.TariffHelper.createDiscountTariff;
import static pl.shalpuk.scooterService.helper.TariffHelper.createPremiumTariff;
import static pl.shalpuk.scooterService.helper.TariffHelper.createRegularTariff;
import static pl.shalpuk.scooterService.helper.UserHelper.createDefaultAdmin;
import static pl.shalpuk.scooterService.helper.UserHelper.createDefaultUser;
import static pl.shalpuk.scooterService.helper.UserHelper.createSuperAdminUser;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestConfiguration.class})
class UserServiceTest {

    @Autowired
    protected UserService userService;
    @Autowired
    protected ScooterService scooterService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected RideService rideService;
    @Autowired
    protected TariffService tariffService;
    @Autowired
    protected DataLoader dataLoader;

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ScooterRepository scooterRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected RideRepository rideRepository;
    @Autowired
    protected TariffRepository tariffRepository;
    @Autowired
    protected PaymentInformationRepository paymentInformationRepository;
    @Autowired
    protected CardRepository cardRepository;

    @BeforeEach
    public void init() {
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

    @Test
    public void testCreateUser_Created() {
        Role role = roleService.getRoleByName(DefaultRoles.USER.getName());

        Assertions.assertEquals(3, userRepository.count());
        Assertions.assertEquals(0, paymentInformationRepository.count());
        Assertions.assertEquals(0, cardRepository.count());

        User user = UserTestHelper.createUser(role);

        Assertions.assertEquals(4, userRepository.count());
        Assertions.assertEquals(1, paymentInformationRepository.count());
        Assertions.assertEquals(2, cardRepository.count());
    }

}