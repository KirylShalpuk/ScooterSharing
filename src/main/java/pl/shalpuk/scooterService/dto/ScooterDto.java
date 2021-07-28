package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class ScooterDto {

    @JsonProperty("id")
    private UUID id;

    @NotNull
    @JsonProperty("manufacturer")
    private String manufacturer;

    @NotNull
    @JsonProperty("model")
    private String model;

    @JsonProperty("photoUrl")
    private String photoUrl;

    @Max(value = 100)
    @Min(value = 0)
    @JsonProperty("batteryCharge")
    private int batteryCharge;

    @JsonProperty("lastService")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime lastService;

    @JsonProperty("softwareVersion")
    private String softwareVersion;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("charging")
    private boolean charging;

    @NotNull
    @JsonProperty("currentLocation")
    private LocationDto currentLocation;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(int batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public LocalDateTime getLastService() {
        return lastService;
    }

    public void setLastService(LocalDateTime lastService) {
        this.lastService = lastService;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public LocationDto getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(LocationDto currentLocation) {
        this.currentLocation = currentLocation;
    }
}
