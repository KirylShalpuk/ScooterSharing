package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.UserListDto;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;

import java.util.Objects;

@Component
public class UserListToDtoConverter implements ToDtoConverter<User, UserListDto> {

    @Override
    public UserListDto convertToDto(User entity) {
        UserListDto dto = new UserListDto();
        BeanUtils.copyProperties(entity, dto);
        Role userRole = entity.getRole();
        if (Objects.nonNull(userRole)) {
            dto.setRole(userRole.getName());
        }

        return dto;
    }
}
