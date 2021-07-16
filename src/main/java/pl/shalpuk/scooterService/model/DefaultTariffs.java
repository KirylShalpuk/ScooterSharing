package pl.shalpuk.scooterService.model;

public enum DefaultTariffs {
    REGULAR("Regular", "Regular tariff"),
    PREMIUM("Premium", "Premium tariff, health insurance included"),
    DISCOUNT("Discount", "Discount -25% tariff");

    private final String name;
    private final String description;

    DefaultTariffs(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
