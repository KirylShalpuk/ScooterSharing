package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ShortScooterDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("manufacturer")
    private String manufacturer;

    @JsonProperty("model")
    private String model;

    @JsonProperty("currentLocation")
    private LocationDto currentLocation;

    public ShortScooterDto() {
    }

    public ShortScooterDto(UUID id, String manufacturer, String model, LocationDto currentLocation) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.currentLocation = currentLocation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocationDto getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LocationDto currentLocation) {
        this.currentLocation = currentLocation;
    }
}
