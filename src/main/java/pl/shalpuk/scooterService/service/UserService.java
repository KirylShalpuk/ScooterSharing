package pl.shalpuk.scooterService.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.converter.entity.PaymentInformationToEntityConverter;
import pl.shalpuk.scooterService.dto.UserActivationDto;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.dto.UserRoleDto;
import pl.shalpuk.scooterService.exception.ServiceException;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.PaymentInformation;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.UserRepository;
import pl.shalpuk.scooterService.util.AuthContext;

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

        Role userRole = roleService.getRoleByName(DefaultRoles.USER.toString());
        request.setRole(userRole);

        User user = userRepository.save(request);
        logger.info("User with phone number {} was created successfully", user.getPhoneNumber());
        return userRepository.save(request);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id = %s is not found", userId)));
    }

    public void deleteUserById(UUID userId) {
        User user = getUserById(userId);

        if (Objects.isNull(user.getRole())) {
            throw new ServiceException("You can not delete super admin");
        }

        userRepository.delete(user);
        logger.info("User with id = {} was deleted successfully", userId);
    }

    public User updateUserById(UUID userId, UserDto updatedUser) {
        User currentUser = getUserById(userId);
        BeanUtils.copyProperties(updatedUser, currentUser, "id", "version", "active", "paymentInformation");

        PaymentInformation paymentInformation =
                paymentInformationToEntityConverter.convertToEntity(updatedUser.getPaymentInformationDto());
        currentUser.setPaymentInformation(paymentInformation);

        currentUser = userRepository.save(currentUser);
        logger.info("User with id = {} was updated successfully", userId);
        return currentUser;
    }

    public void activateUser(UUID userId, UserActivationDto dto) {
        User user = getUserById(userId);

        //TODO: if statement must be updated
        if (isStatusChanged(user, dto) && dto.getAccessCode().equals("1111")) {
            user.setActive(dto.isAccessStatus());
            userRepository.save(user);
            logger.info("User with id = {} was activated", userId);
        }
    }

    public void deactivateUser(UUID userId) {
        User user = getUserById(userId);
        if (isCurrentUserFromAuth(userId)) {
            throw new ServiceException(String.format("User [%s] can not deactivate himself", userId));
        }

        if (Objects.isNull(user)) {
            throw new ServiceException(String.format("User [%s] can not be deactivated", userId));
        }
        userRepository.save(user);
    }

    private boolean isStatusChanged(User user, UserActivationDto dto) {
        if (Objects.nonNull(user) && Objects.nonNull(dto)) {
            return user.isActive() != dto.isAccessStatus();
        }

        return false;
    }

    public User getActiveUserByEmail(String email) {
        return userRepository.getUserByEmailAndActiveIsTrue(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Active user with email = %s is not found", email)));
    }

    public Page<User> getAllUsersPage(PageRequest pageRequest, String search) {
        if (StringUtils.isEmpty(search)) {
            return userRepository.findAll(pageRequest);
        } else {
            return userRepository.getAllByEmailIgnoreCaseContaining(search, pageRequest);
        }
    }

    public User updateUserRole(UUID userId, UserRoleDto roleDto) {
        User user = getUserById(userId);

        if (isCurrentUserFromAuth(userId)) {
            throw new ServiceException(String.format("User [%s] can not change role himself", userId));
        }

        String roleName = roleDto.getRole().toString();
        Role role = roleService.getRoleByName(roleName);
        user.setRole(role);

        user = userRepository.save(user);
        logger.info("Role [{}] for user with id = {} was assigned successfully", roleName, userId);

        return user;
    }

    private boolean isCurrentUserFromAuth(UUID userId) {
        User userFromContext = AuthContext.getAuthContext();
        return userId.equals(userFromContext.getId());
    }
}
