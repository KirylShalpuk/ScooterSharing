package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.Card;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.PaymentInformation;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.model.UserLocation;

import java.time.LocalDateTime;

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

    public static User createDefaultUser(Role userRole, Location location) {
        Card card = new Card();
        card.setMain(true);
        card.setDateExpiration("05/2099");
        card.setCardNumber("8888 8888 8888 8888");
        card.setCardHolder("Kiryl Shalpuk");
        card.setEmail("kiryl.shalpuk@scooter.com");

        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setPhoneNumber("+48798743379");
        paymentInformation.setCountry("Poland");
        paymentInformation.setAddress("Warsaw");
        paymentInformation.setPostCode("00-001");
        paymentInformation.addCard(card);

        UserLocation userLocation = new UserLocation();
        userLocation.setLocation(location);
        userLocation.setPositionTime(LocalDateTime.now().minusMinutes(30));

        User user = new User();
        user.setFirstName("Kiryl");
        user.setLastName("Shalpuk");
        user.setPhoneNumber("+48798743379");
        user.setEmail("kiryl.shalpuk@scooter.com");
        user.setPassword("$2a$10$0sBp8KSHQW/VuTjGJSnoO.nbRcr3mcfStc5BxA5s61A4Mt2vU6iq."); //1-8
        user.setActive(true);
        user.setRole(userRole);
        user.setPaymentInformation(paymentInformation);
        user.addUserLocation(userLocation);
        return user;
    }

}
