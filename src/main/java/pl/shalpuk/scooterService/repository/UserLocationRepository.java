package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.UserLocation;

import java.util.UUID;

public interface UserLocationRepository extends JpaRepository<UserLocation, UUID> {
}
