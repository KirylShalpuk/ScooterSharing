package pl.shalpuk.scooterService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import pl.shalpuk.scooterService.model.Ride;

import java.util.Set;
import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID>, JpaSpecificationExecutor<Ride> {

    Page<Ride> getAllByUserEmailIgnoreCaseContaining(String email, @Nullable Specification<Ride> spec, Pageable pageable);

    @Query(value = "SELECT r.user.email FROM Ride r")
    Set<String> getAllUserEmail();

    @Query(value = "SELECT rl.location.street FROM Ride r JOIN r.rideLocations rl")
    Set<String> getAllLocationAddresses();
}
