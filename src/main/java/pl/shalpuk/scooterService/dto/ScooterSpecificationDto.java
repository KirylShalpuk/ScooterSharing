package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

public class ScooterSpecificationDto {

    @JsonProperty("manufacturers")
    private Set<String> manufacturers;

    @JsonProperty("models")
    private Set<String> models;

    @Min(0)
    @Max(100)
    @JsonProperty("batteryChargeFrom")
    private int batteryChargeFrom;

    @Min(0)
    @Max(100)
    @JsonProperty("batteryChargeTo")
    private int batteryChargeTo;

    @JsonProperty("locationAddress")
    private Set<String> locationAddress;

    @JsonProperty("active")
    private boolean active;

    public Set<String> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Set<String> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public Set<String> getModels() {
        return models;
    }

    public void setModels(Set<String> models) {
        this.models = models;
    }

    public int getBatteryChargeFrom() {
        return batteryChargeFrom;
    }

    public void setBatteryChargeFrom(int batteryChargeFrom) {
        this.batteryChargeFrom = batteryChargeFrom;
    }

    public int getBatteryChargeTo() {
        return batteryChargeTo;
    }

    public void setBatteryChargeTo(int batteryChargeTo) {
        this.batteryChargeTo = batteryChargeTo;
    }

    public Set<String> getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(Set<String> locationAddress) {
        this.locationAddress = locationAddress;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
