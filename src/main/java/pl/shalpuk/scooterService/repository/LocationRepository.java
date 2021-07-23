package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<LocationRepository, UUID> {
}
