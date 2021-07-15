package pl.shalpuk.scooterService.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
public class Ride extends AbstractPersistentObject{

//    @OneToOne
//    private User user;
//
//    @OneToOne
//    private Scooter scooter;
//
//    private LocalDateTime startRideTime;
//    private LocalDateTime endRideTime;
//
//    @OneToOne
//    private Location startLocation;


}
