package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.model.User;

@Component
public class UserToDtoConverter implements ToDtoConverter<User, UserDto>{

    @Override
    public UserDto convertToDto(User entity) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(entity, userDto, "password");
        return userDto;
    }
}
