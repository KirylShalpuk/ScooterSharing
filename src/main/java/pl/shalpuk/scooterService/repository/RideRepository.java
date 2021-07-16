package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.Ride;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

}
