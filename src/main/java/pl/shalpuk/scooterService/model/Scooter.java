package pl.shalpuk.scooterService.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "scooters")
public class Scooter extends AbstractPersistentObject{

    private String manufacturer;
    private String model;
    private int batteryCharge;
    private Date lastService;
    private String softwareVersion;
    private boolean activate;

//    @OneToOne
//    private Location currentLocation;


}
