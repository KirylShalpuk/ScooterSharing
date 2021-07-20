package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.Role;

import javax.persistence.EntityNotFoundException;

class RoleServiceTest extends AbstractJunitTest {

    @Test
    public void testGetRoleById_RoleExists_Success() {
        Role role = roleService.getRoleByName(DefaultRoles.USER.getName());
        Assertions.assertNotNull(role);
    }

    @Test
    public void testGetRoleById_RoleNotExist_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> roleService.getRoleByName("super admin"));
    }

}