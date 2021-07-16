package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.Tariff;

import java.util.UUID;

public interface TariffRepository extends JpaRepository<Tariff, UUID> {

}
