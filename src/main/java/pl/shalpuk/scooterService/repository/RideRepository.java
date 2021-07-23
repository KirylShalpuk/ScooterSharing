package pl.shalpuk.scooterService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.shalpuk.scooterService.model.Ride;

import java.util.UUID;

public interface RideRepository extends JpaRepository<Ride, UUID> {

//    @Query(value = "SELECT * FROM rides r JOIN users ur ON r.user_id = ur.id " +
//            "WHERE LOWER(ur.email) LIKE CONCAT('%', LOWER(:email), '%')", nativeQuery = true)
    Page<Ride> getAllByUserEmailIgnoreCaseContaining(String email, Pageable pageable);

}
