package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
public class Ride extends AbstractPersistentObject implements Serializable {

    private static final long serialVersionUID = 2756167777311379182L;

    @Column(name = "start_time")
    private LocalDateTime startRideTime;

    @Column(name = "end_time")
    private LocalDateTime endRideTime;

    @Column(name = "ride_status")
    @Enumerated(value = EnumType.STRING)
    private RideStatus rideStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "scooter_id")
    @JsonManagedReference
    private Scooter scooter;

    @ManyToOne
    @JoinColumn(name = "tariff_id")
    @JsonManagedReference
    private Tariff tariff;

//    @OneToOne
//    private Location startLocation;


    public LocalDateTime getStartRideTime() {
        return startRideTime;
    }

    public void setStartRideTime(LocalDateTime startRideTime) {
        this.startRideTime = startRideTime;
    }

    public LocalDateTime getEndRideTime() {
        return endRideTime;
    }

    public void setEndRideTime(LocalDateTime endRideTime) {
        this.endRideTime = endRideTime;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Scooter getScooter() {
        return scooter;
    }

    public void setScooter(Scooter scooter) {
        this.scooter = scooter;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }
}
