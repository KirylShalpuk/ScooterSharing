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

    private final UserToEntityConverter entityConverter;
    private final UserToDtoConverter dtoConverter;


    public UserController(Logger logger,
                          UserService userService,
                          UserToEntityConverter entityConverter,
                          UserToDtoConverter dtoConverter) {
        this.logger = logger;
        this.userService = userService;
        this.entityConverter = entityConverter;
        this.dtoConverter = dtoConverter;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto request) {
        User fromDto = entityConverter.convertToEntity(request);
        User createdUser = userService.createUser(fromDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(createdUser));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(dtoConverter.convertToDto(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserById(@PathVariable UUID userId, @Valid @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUserById(userId, userDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(updatedUser));
    }

    @PutMapping("/{userId}/activate")
    public ResponseEntity<?> activateUser(@PathVariable UUID userId, @RequestBody UserActivationDto userActivationDto) {
        userService.activateUser(userId, userActivationDto);
        return ResponseEntity.ok().build();
    }

}
