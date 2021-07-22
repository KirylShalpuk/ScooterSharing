package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_locations")
public class UserLocation extends AbstractPersistentObject implements Serializable {

    private static final long serialVersionUID = 377973157234517177L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "location_id")
    private Location location;

    @JoinColumn(name = "position_time")
    private LocalDateTime positionTime;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
