package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.converter.entity.PaymentInformationToEntityConverter;
import pl.shalpuk.scooterService.dto.UserActivationDto;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.PaymentInformation;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.UserRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.UUID;


@Service
public class UserService {

    private final Logger logger;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PaymentInformationToEntityConverter paymentInformationToEntityConverter;

    public UserService(Logger logger,
                       UserRepository userRepository,
                       RoleService roleService,
                       PaymentInformationToEntityConverter paymentInformationToEntityConverter) {
        this.logger = logger;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.paymentInformationToEntityConverter = paymentInformationToEntityConverter;
    }

    public User createUser(User request) {
        String email = request.getEmail();
        String phoneNumber = request.getPhoneNumber();
        if (userRepository.existsByPhoneNumberOrEmail(phoneNumber, email)) {
            throw new EntityExistsException(
                    String.format("User with email = %s or phone number = %s already exists", email, phoneNumber));
        }

        Role userRole = roleService.getRoleByName(DefaultRoles.USER.getName());
        request.setRole(userRole);

        User user = userRepository.save(request);
        logger.info(String.format("User with phone number %s was created successfully", user.getPhoneNumber()));
        return userRepository.save(request);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id = %s is not found", userId)));
    }

    public void deleteUserById(UUID userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
        logger.info(String.format("User with id = %s was deleted successfully", userId));
    }

    public User updateUserById(UUID userId, UserDto updatedUser) {
        User currentUser = getUserById(userId);
        BeanUtils.copyProperties(updatedUser, currentUser, "id", "version", "active", "paymentInformation");

        PaymentInformation paymentInformation = paymentInformationToEntityConverter.convertToEntity(updatedUser.getPaymentInformationDto());
        currentUser.setPaymentInformation(paymentInformation);

        currentUser = userRepository.save(currentUser);
        logger.info(String.format("User with id = %s was updated successfully", userId));
        return currentUser;
    }

    public void activateUser(UUID userId, UserActivationDto dto) {
        User user = getUserById(userId);

        //TODO: if statement will be updated
        if (isStatusChanged(user, dto) && dto.getAccessCode().equals("1111")) {
            user.setActive(dto.isAccessStatus());
            userRepository.save(user);
            logger.info(String.format("User with id = %s was activated", userId));
        }
    }

    public void deactivateUser(UUID userId) {
        User user = getUserById(userId);
        User userFromContext = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getId().equals(userFromContext.getId()))
        userRepository.save(user);

    }

    private boolean isStatusChanged(User user, UserActivationDto dto) {
        if (Objects.nonNull(user) && Objects.nonNull(dto)) {
            return user.isActive() != dto.isAccessStatus();
        }

        return false;
    }

    public User getActiveUserByEmail(String email) {
        return userRepository.getUserByEmailAndActiveIsTrue(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("Active user with email = %s is not found", email)));
    }
}
