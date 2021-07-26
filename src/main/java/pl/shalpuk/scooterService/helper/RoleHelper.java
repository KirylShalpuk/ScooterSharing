package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.Role;

public class RoleHelper {

    public static Role createDefaultAdminRole() {
        Role admin = new Role();
        admin.setName(DefaultRoles.ADMIN.toString());
        admin.setDescription(DefaultRoles.ADMIN.getDescription());
        admin.setActive(true);
        admin.setAdmin(true);
        return admin;
    }

    public static Role createDefaultUserRole() {
        Role user = new Role();
        user.setName(DefaultRoles.VIEWER.toString());
        user.setDescription(DefaultRoles.VIEWER.getDescription());
        user.setActive(true);
        return user;
    }

}
