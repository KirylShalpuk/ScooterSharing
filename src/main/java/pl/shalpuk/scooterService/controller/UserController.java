package pl.shalpuk.scooterService.controller;


import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.UserListToDtoConverter;
import pl.shalpuk.scooterService.converter.dto.UserToDtoConverter;
import pl.shalpuk.scooterService.converter.entity.UserToEntityConverter;
import pl.shalpuk.scooterService.dto.UserActivationDto;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.dto.UserListDto;
import pl.shalpuk.scooterService.dto.UserRoleDto;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.model.UserSortingField;
import pl.shalpuk.scooterService.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final Logger logger;
    private final UserService userService;

    private final UserToEntityConverter entityConverter;
    private final UserToDtoConverter dtoConverter;
    private final UserListToDtoConverter userListToDtoConverter;


    public UserController(Logger logger,
                          UserService userService,
                          UserToEntityConverter entityConverter,
                          UserToDtoConverter dtoConverter,
                          UserListToDtoConverter userListToDtoConverter) {
        this.logger = logger;
        this.userService = userService;
        this.entityConverter = entityConverter;
        this.dtoConverter = dtoConverter;
        this.userListToDtoConverter = userListToDtoConverter;
    }


    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserDto request) {
        User fromDto = entityConverter.convertToEntity(request);
        User createdUser = userService.createUser(fromDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(createdUser));
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(dtoConverter.convertToDto(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserListDto>> getAllUsersPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) int page,
            @RequestParam(value = "elements", defaultValue = "20", required = false) @Min(20) @Max(50) int elements,
            @RequestParam(value = "sortDirection", defaultValue = "ASC", required = false) Sort.Direction sortDirection,
            @RequestParam(value = "sortBy", defaultValue = "EMAIL", required = false) UserSortingField sortBy,
            @RequestParam(value = "search", defaultValue = "", required = false) @Min(2) String search) {
        PageRequest pageRequest = PageRequest.of(page, elements, sortDirection, sortBy.getSortField());
        Page<User> userPage = userService.getAllUsersPage(pageRequest, search);
        return ResponseEntity.ok(userListToDtoConverter.convertToDto(userPage));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserById(@PathVariable UUID userId, @Valid @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUserById(userId, userDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(updatedUser));
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN')")
    @PutMapping("/{userId}/activate")
    public ResponseEntity<?> activateUser(@PathVariable UUID userId, @RequestBody UserActivationDto userActivationDto) {
        userService.activateUser(userId, userActivationDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable UUID userId) {
        userService.deactivateUser(userId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{userId}/updateRole")
    public ResponseEntity<?> updateUserRole(@PathVariable UUID userId, @RequestBody UserRoleDto roleDto) {
        User user = userService.updateUserRole(userId, roleDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(user));
    }

}
