package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.shalpuk.scooterService.model.DefaultRoles;

public class UserRoleDto {

    @JsonProperty("role")
    private DefaultRoles role;

    public DefaultRoles getRole() {
        return role;
    }

    public void setRole(DefaultRoles role) {
        this.role = role;
    }
}
