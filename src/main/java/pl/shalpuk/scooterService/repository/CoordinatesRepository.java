package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.Coordinates;

import java.util.UUID;

public interface CoordinatesRepository extends JpaRepository<Coordinates, UUID> {
}
