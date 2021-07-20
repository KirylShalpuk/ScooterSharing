package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.PaymentInformation;

import java.util.UUID;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, UUID> {
}
