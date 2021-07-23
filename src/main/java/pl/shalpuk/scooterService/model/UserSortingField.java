package pl.shalpuk.scooterService.model;

public enum UserSortingField {

    LAST_NAME("lastName"),
    PHONE_NUMBER("phoneNumber"),
    EMAIL("email"),
    ROLE("role"),
    ACTIVE("active");

    private final String sortField;

    UserSortingField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortField() {
        return sortField;
    }
}
