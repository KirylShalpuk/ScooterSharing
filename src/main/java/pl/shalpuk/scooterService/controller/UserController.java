package pl.shalpuk.scooterService.controller;


import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.shalpuk.scooterService.converter.dto.UserToDtoConverter;
import pl.shalpuk.scooterService.converter.entity.UserToEntityConverter;
import pl.shalpuk.scooterService.dto.UserActivationDto;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.service.UserService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger;
    private final UserService userService;

    private final UserToEntityConverter userToEntityConverter;
    private final UserToDtoConverter userToDtoConverter;


    public UserController(Logger logger,
                          UserService userService,
                          UserToEntityConverter userToEntityConverter,
                          UserToDtoConverter userToDtoConverter) {
        this.logger = logger;
        this.userService = userService;
        this.userToEntityConverter = userToEntityConverter;
        this.userToDtoConverter = userToDtoConverter;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto request) {
        User userFromDto = userToEntityConverter.convertToEntity(request);
        User userFromDB = userService.createUser(userFromDto);
        return ResponseEntity.ok(userToDtoConverter.convertToDto(userFromDB));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(userToDtoConverter.convertToDto(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserById(@PathVariable UUID userId, @Valid @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUserById(userId, userDto);
        return ResponseEntity.ok(userToDtoConverter.convertToDto(updatedUser));
    }

    @PutMapping("/{userId}/activate")
    public ResponseEntity<?> activateUser(@PathVariable UUID userId, @RequestBody UserActivationDto userActivationDto) {
        userService.activateUser(userId, userActivationDto);
        return ResponseEntity.ok().build();
    }

}
