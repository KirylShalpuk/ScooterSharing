package pl.shalpuk.scooterService.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
public class Location extends AbstractPersistentObject{

    private String country;
    private String city;
    private String address;

//    @OneToOne
//    private Coordinate coordinate;

}
