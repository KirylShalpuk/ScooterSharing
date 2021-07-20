package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.Card;

import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
}
