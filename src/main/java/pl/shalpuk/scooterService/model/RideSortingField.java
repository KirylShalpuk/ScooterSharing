package pl.shalpuk.scooterService.model;

public enum RideSortingField {

    START_TIME("startRideTime"),
    RIDE_STATUS("rideStatus"),
    EMAIL("user.email"),
    SCOOTER("scooter.model"),
    TARIFF("tariff.name");

    private final String sortField;

    RideSortingField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortField() {
        return sortField;
    }
}
