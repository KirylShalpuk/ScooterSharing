package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.Location;

import java.util.UUID;

public interface CoordinatesRepository extends JpaRepository<Location, UUID> {
}
