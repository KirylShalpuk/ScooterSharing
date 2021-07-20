package pl.shalpuk.scooterService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tariffs")
public class Tariff extends AbstractPersistentObject implements Serializable {

    private static final long serialVersionUID = 1425354613896396667L;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "costs")
    private int costs;

    @Column(name = "active")
    private boolean active;

    @OneToMany
    @JsonBackReference
    private Set<Ride> rides;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCosts() {
        return costs;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
}
