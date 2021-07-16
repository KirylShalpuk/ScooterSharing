package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

public class ScooterDto {

    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("model")
    private String model;
    @JsonProperty("photo_url")
    private String photoUrl;
    @Max(value = 100)
    @Min(value = 0)
    @JsonProperty("battery_charge")
    private int batteryCharge;
    @JsonProperty("last_service")
    private Date lastService;
    @JsonProperty("software_version")
    private String softwareVersion;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("charging")
    private boolean charging;

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

    public Date getLastService() {
        return lastService;
    }

    public void setLastService(Date lastService) {
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
}
