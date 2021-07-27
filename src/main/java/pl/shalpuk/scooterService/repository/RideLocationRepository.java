package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.RideLocation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RideLocationRepository extends JpaRepository<RideLocation, UUID> {

    List<RideLocation> getAllByPositionTimeBetween(LocalDateTime from, LocalDateTime to);
}
