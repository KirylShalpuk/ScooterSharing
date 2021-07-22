package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.JwtToken;

import java.util.Optional;
import java.util.UUID;

public interface JwtTokenRepository extends JpaRepository<JwtToken, UUID> {

    Optional<JwtToken> findByUserIdAndActiveIsTrue(UUID userId);

    Optional<JwtToken> findByTokenAndActiveIsTrue(String token);
}
