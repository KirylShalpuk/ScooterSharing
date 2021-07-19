package pl.shalpuk.scooterService.helper;

import pl.shalpuk.scooterService.model.DefaultTariffs;
import pl.shalpuk.scooterService.model.Tariff;

public class TariffHelper {

    public static Tariff createRegularTariff() {
        Tariff regularTariff = new Tariff();
        regularTariff.setName(DefaultTariffs.REGULAR.getName());
        regularTariff.setDescription(DefaultTariffs.REGULAR.getDescription());
        regularTariff.setCosts(8);
        regularTariff.setActive(true);

        return regularTariff;
    }

    public static Tariff createPremiumTariff() {
        Tariff premiumTariff = new Tariff();
        premiumTariff.setName(DefaultTariffs.PREMIUM.getName());
        premiumTariff.setDescription(DefaultTariffs.PREMIUM.getDescription());
        premiumTariff.setCosts(12);
        premiumTariff.setActive(true);

        return premiumTariff;
    }

    public static Tariff createDiscountTariff() {
        Tariff discountTariff = new Tariff();
        discountTariff.setName(DefaultTariffs.DISCOUNT.getName());
        discountTariff.setDescription(DefaultTariffs.DISCOUNT.getDescription());
        discountTariff.setCosts(6);
        discountTariff.setActive(true);

        return discountTariff;
    }

}
