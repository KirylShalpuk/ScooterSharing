package pl.shalpuk.scooterService.model;

public enum ScooterSortingField {

    MANUFACTURER("manufacturer"),
    MODEL("model"),
    BATTERY_CHARGE("batteryCharge"),
    ADDRESS("currentLocation.address");

    private final String sortField;

    ScooterSortingField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortField() {
        return sortField;
    }
}
