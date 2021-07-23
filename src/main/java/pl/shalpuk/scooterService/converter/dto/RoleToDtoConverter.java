package pl.shalpuk.scooterService.converter.dto;

import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.RoleDto;
import pl.shalpuk.scooterService.model.Role;

@Component
public class RoleToDtoConverter implements ToDtoConverter<Role, RoleDto> {

    @Override
    public RoleDto convertToDto(Role entity) {
        RoleDto dto = new RoleDto();
        dto.setRole(entity.getName());
        return dto;
    }
}
