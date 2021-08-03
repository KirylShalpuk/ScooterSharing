package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "scooters")
public class Scooter extends AbstractPersistentObject implements Serializable {

    private static final long serialVersionUID = -565959792581229320L;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "battery_charge")
    private int batteryCharge;

    @Column(name = "last_service")
    private LocalDateTime lastService;

    @Column(name = "software_version")
    private String softwareVersion;

    @Column(name = "active")
    private boolean active;

    @Column(name = "charging")
    private boolean charging;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ScooterStatus scooterStatus;

    @OneToMany(mappedBy = "scooter")
    @JsonBackReference
    private Set<Ride> rides;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location currentLocation;

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

    public ScooterStatus getScooterStatus() {
        return scooterStatus;
    }

    public void setScooterStatus(ScooterStatus scooterStatus) {
        this.scooterStatus = scooterStatus;
    }

    public Set<Ride> getRides() {
        return rides;
    }

    public void setRides(Set<Ride> rides) {
        if (this.rides == null) {
            this.rides = rides;
        } else {
            this.rides.clear();
            this.rides.addAll(rides);
        }
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
