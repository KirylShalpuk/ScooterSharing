package pl.shalpuk.scooterService.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "coordinates")
public class Coordinate extends AbstractPersistentObject {

    private String latitude;
    private String longitude;


}
