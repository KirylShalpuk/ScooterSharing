package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.Card;
import pl.shalpuk.scooterService.model.RideLocation;

import java.util.UUID;

public interface RideLocationRepository extends JpaRepository<RideLocation, UUID> {
}
