package pl.shalpuk.scooterService.converter.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.model.User;

@Component
public class UserToEntityConverter implements ToEntityConverter<UserDto, User> {

    @Override
    public User convertToEntity(UserDto dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity,"id");
        return entity;
    }
}
