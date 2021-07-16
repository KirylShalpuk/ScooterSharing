package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.model.User;

@Component
public class UserToDtoConverter implements ToDtoConverter<User, UserDto>{

    @Override
    public UserDto convertToDto(User entity) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entity, dto, "password");
        return dto;
    }
}
