package pl.shalpuk.scooterService.model;

public enum DefaultRoles {

    ADMIN("System admin", "Default admin role"),
    USER("System user", "Default user role");

    private final String name;
    private final String description;

    DefaultRoles(String name, String description) {
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
