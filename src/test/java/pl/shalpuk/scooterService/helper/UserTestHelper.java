package pl.shalpuk.scooterService.helper;

import com.google.common.collect.Sets;
import pl.shalpuk.scooterService.model.Card;
import pl.shalpuk.scooterService.model.PaymentInformation;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;

public class UserTestHelper {

    public static User createUser(Role role) {
        Card mainCard = new Card();
        mainCard.setCardHolder("Card Holder");
        mainCard.setCardNumber("1111-1111-1111-1111");
        mainCard.setEmail("card.holder@gmail.com");
        mainCard.setMain(true);
        mainCard.setDateExpiration("05/2999");

        Card simpleCard = new Card();
        simpleCard.setCardHolder("Card Holder");
        simpleCard.setCardNumber("2222-2222-2222-2222");
        simpleCard.setEmail("card.holder@gmail.com");
        simpleCard.setDateExpiration("05/2999");

        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setAddress("Warsaw");
        paymentInformation.setCountry("Poland");
        paymentInformation.setPostCode("00-001");
        paymentInformation.setPhoneNumber("+375375375375");
        paymentInformation.setCards(Sets.newHashSet(mainCard, simpleCard));

        User user = new User();
        user.setPhoneNumber("+375375375375");
        user.setFirstName("first name");
        user.setLastName("last name");
        user.setPassword("password");
        user.setEmail("email");
        user.setPaymentInformation(paymentInformation);
        user.setRole(role);

        return user;
    }
}
