package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;

public class UserHelper {

    public static User createSuperAdminUser() {
        User superAdmin = new User();
        superAdmin.setActive(true);
        superAdmin.setPhoneNumber("+48798743377");
        superAdmin.setEmail("super.admin@scooter.com");
        superAdmin.setPassword("$2a$10$0sBp8KSHQW/VuTjGJSnoO.nbRcr3mcfStc5BxA5s61A4Mt2vU6iq."); //1-8
        return superAdmin;
    }

    public static User createDefaultAdmin(Role adminRole) {
        User admin = new User();
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPhoneNumber("+48798743378");
        admin.setEmail("admin@scooter.com");
        admin.setPassword("$2a$10$0sBp8KSHQW/VuTjGJSnoO.nbRcr3mcfStc5BxA5s61A4Mt2vU6iq."); //1-8
        admin.setActive(true);
        admin.setRole(adminRole);
        return admin;
    }

    public static User createDefaultUser(Role userRole) {
        User user = new User();
        user.setFirstName("Kiryl");
        user.setLastName("Shalpuk");
        user.setPhoneNumber("+48798743379");
        user.setEmail("kiryl.shalpuk@scooter.com");
        user.setPassword("$2a$10$0sBp8KSHQW/VuTjGJSnoO.nbRcr3mcfStc5BxA5s61A4Mt2vU6iq."); //1-8
        user.setActive(true);
        user.setRole(userRole);
        return user;
    }

}
