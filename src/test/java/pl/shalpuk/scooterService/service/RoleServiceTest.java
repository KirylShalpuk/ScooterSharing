package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.Role;

import javax.persistence.EntityNotFoundException;

class RoleServiceTest extends AbstractIntegrationServiceTest {

    @Test
    public void testGetRoleById_RoleExists_Success() {
        Role role = roleService.getRoleByName(DefaultRoles.USER.toString());
        Assertions.assertNotNull(role);
    }

    @Test
    public void testGetRoleById_RoleNotExist_EntityNotFoundException() {
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> roleService.getRoleByName("super admin"));
    }

    @Test
    public void testGetAllRolesPage_TwoRoleExist_ReturnTwoRolesOnPage(){
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.Direction.ASC, "name");
        Page<Role> rolePage = roleService.getAllRolesPage(pageRequest);

        Assertions.assertEquals(2, roleRepository.count());
        Assertions.assertEquals(2, rolePage.getTotalElements());
    }

}