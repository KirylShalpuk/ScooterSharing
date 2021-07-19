package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.Role;

public class RoleTestHelper {

    public static Role createCustomUserRole() {
        Role role = new Role();
        role.setName("Custom role");
        role.setDescription("It is the custom role");
        return role;
    }
}
