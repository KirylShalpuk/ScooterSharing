package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.UserListDto;
import pl.shalpuk.scooterService.model.User;

@Component
public class UserListToDtoConverter implements ToDtoConverter<User, UserListDto> {

    @Override
    public UserListDto convertToDto(User entity) {
        UserListDto dto = new UserListDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setRole(entity.getRole().getName());

        return dto;
    }
}
