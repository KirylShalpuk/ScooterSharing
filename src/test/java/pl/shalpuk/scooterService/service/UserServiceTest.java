package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.shalpuk.scooterService.helper.UserTestHelper;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;

import static pl.shalpuk.scooterService.helper.RoleHelper.createDefaultAdminRole;
import static pl.shalpuk.scooterService.helper.RoleHelper.createDefaultUserRole;
import static pl.shalpuk.scooterService.helper.ScooterHelper.createScooters;
import static pl.shalpuk.scooterService.helper.TariffHelper.createDiscountTariff;
import static pl.shalpuk.scooterService.helper.TariffHelper.createPremiumTariff;
import static pl.shalpuk.scooterService.helper.TariffHelper.createRegularTariff;
import static pl.shalpuk.scooterService.helper.UserHelper.createDefaultAdmin;
import static pl.shalpuk.scooterService.helper.UserHelper.createDefaultUser;
import static pl.shalpuk.scooterService.helper.UserHelper.createSuperAdminUser;

class UserServiceTest extends AbstractJunitContext {

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