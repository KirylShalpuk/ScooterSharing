package pl.shalpuk.scooterService.service;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.shalpuk.scooterService.dto.CardDto;
import pl.shalpuk.scooterService.dto.PaymentInformationDto;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.helper.UserTestHelper;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.UUID;

class UserServiceTest extends AbstractJunitTest {

    @Test
    public void testCreateUser_UserNotExist_Created() {
        Role role = roleService.getRoleByName(DefaultRoles.USER.toString());

        Assertions.assertEquals(3, userRepository.count());
        Assertions.assertEquals(0, paymentInformationRepository.count());
        Assertions.assertEquals(0, cardRepository.count());

        User user = UserTestHelper.createUser(role);
        userService.createUser(user);

        Assertions.assertEquals(4, userRepository.count());
        Assertions.assertEquals(1, paymentInformationRepository.count());
        Assertions.assertEquals(2, cardRepository.count());
    }

    @Test
    public void testCreateUser_UserExists_EntityExistsException() {
        Role role = roleService.getRoleByName(DefaultRoles.USER.toString());
        User user = UserTestHelper.createUser(role);
        userRepository.save(user);

        Assertions.assertThrows(EntityExistsException.class,
                () -> userService.createUser(user));
    }

    @Test
    public void testDeleteUserById_UserExists_Deleted() {
        Role role = roleService.getRoleByName(DefaultRoles.USER.toString());
        User user = userRepository.save(UserTestHelper.createUser(role));

        Assertions.assertEquals(4, userRepository.count());
        userService.deleteUserById(user.getId());
        Assertions.assertEquals(3, userRepository.count());
    }

    @Test
    public void testDeleteUserById_UserNotExist_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.deleteUserById(UUID.randomUUID()));
    }

    @Test
    public void testGetUserById_UserExists_Success() {
        Role role = roleService.getRoleByName(DefaultRoles.USER.toString());
        User user = userRepository.save(UserTestHelper.createUser(role));

        User userFromDb = userService.getUserById(user.getId());
        Assertions.assertEquals(user.getId(), userFromDb.getId());
    }

    @Test
    public void testGetUserById_UserNotExist_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.getUserById(UUID.randomUUID()));
    }

    @Test
    public void testUpdateUserById_UserExists_Updated() {
        Role role = roleService.getRoleByName(DefaultRoles.USER.toString());
        User user = userRepository.save(UserTestHelper.createUser(role));

        CardDto cardDto = new CardDto();
        cardDto.setCardNumber("5555-5555-5555-5555");
        cardDto.setCardHolder(user.getFirstName().concat(" ").concat(user.getLastName()));
        cardDto.setDateExpiration("12/2987");

        PaymentInformationDto paymentInformationDto = new PaymentInformationDto();
        paymentInformationDto.setAddress("Gdansk");
        paymentInformationDto.setCountry("Poland");
        paymentInformationDto.setPostCode("00-001");
        paymentInformationDto.setPhoneNumber(user.getPhoneNumber());
        paymentInformationDto.setCards(Sets.newHashSet(cardDto));

        UserDto updateRequest = new UserDto();
        updateRequest.setEmail(user.getEmail());
        updateRequest.setFirstName(user.getFirstName());
        updateRequest.setLastName("updated lastname");
        updateRequest.setPassword(user.getPassword());
        updateRequest.setPhoneNumber(user.getPhoneNumber());
        updateRequest.setActive(true);
        updateRequest.setPaymentInformationDto(paymentInformationDto);

        Assertions.assertEquals(2, cardRepository.count());

        UUID oldPaymentInformationId = user.getPaymentInformation().getId();
        UUID oldCardId = user.getPaymentInformation().getCards().stream().findFirst().get().getId();

        User updatedUser = userService.updateUserById(user.getId(), updateRequest);

        Assertions.assertEquals(updateRequest.getLastName(), updatedUser.getLastName());
        Assertions.assertFalse(updatedUser.isActive());
        Assertions.assertEquals(1, cardRepository.count());
        Assertions.assertEquals("Gdansk",
                updatedUser.getPaymentInformation().getAddress());
        Assertions.assertFalse(paymentInformationRepository.existsById(oldPaymentInformationId));
        Assertions.assertFalse(cardRepository.existsById(oldCardId));
    }

    @Test
    public void testUpdateUserById_UserNotExist_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.updateUserById(UUID.randomUUID(), new UserDto()));
    }
}