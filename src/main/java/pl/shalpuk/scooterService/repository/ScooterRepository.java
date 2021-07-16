package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.Scooter;

import java.util.UUID;

public interface ScooterRepository extends JpaRepository<Scooter, UUID> {

}
