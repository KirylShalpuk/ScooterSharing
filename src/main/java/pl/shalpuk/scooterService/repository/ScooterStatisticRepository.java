package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.shalpuk.scooterService.model.ScooterStatistic;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScooterStatisticRepository extends JpaRepository<ScooterStatistic, UUID>{

    @Query(value = "SELECT AVG(ss.count), ss.time FROM ScooterStatistic ss WHERE ss.location.street=:street " +
            "AND ss.location.building=:building AND ss.date >= :fromDate GROUP BY ss.time, ss.location.id ORDER BY ss.time")
    List<Object[]> getAllScooterStatisticForLastMonth(
            @Param("street") String street,
            @Param("building") String building,
            @Param("fromDate") LocalDate fromDate);
}
