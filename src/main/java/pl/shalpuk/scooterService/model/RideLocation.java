package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ride_locations")
public class RideLocation extends AbstractPersistentObject implements Serializable {

    private static final long serialVersionUID = 6836958386957489027L;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    @JsonBackReference
    private Ride ride;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "position_time")
    private LocalDateTime positionTime;

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(LocalDateTime positionTime) {
        this.positionTime = positionTime;
    }
}
