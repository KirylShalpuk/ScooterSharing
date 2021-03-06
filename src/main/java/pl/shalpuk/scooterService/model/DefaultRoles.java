package pl.shalpuk.scooterService.model;

public enum DefaultRoles {

    ADMIN("Default admin role"),
    USER("Default user role");

    private final String description;

    DefaultRoles(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
