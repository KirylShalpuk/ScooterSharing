package pl.shalpuk.scooterService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pl.shalpuk.scooterService.model.Scooter;

import java.util.Set;
import java.util.UUID;

public interface ScooterRepository extends JpaRepository<Scooter, UUID>, JpaSpecificationExecutor<Scooter> {

    @Query(value = "SELECT DISTINCT s.manufacturer from Scooter s")
    Set<String> getAllManufacturers();

    @Query(value = "SELECT DISTINCT s.manufacturer from Scooter s where s.active = true")
    Set<String> getAllManufacturersActiveScooters();

    @Query(value = "SELECT DISTINCT s.model from Scooter s")
    Set<String> getAllModels();

    @Query(value = "SELECT DISTINCT s.model from Scooter s where s.active = true")
    Set<String> getAllModelsActiveScooters();

    @Query(value = "SELECT DISTINCT s.currentLocation.street from Scooter s")
    Set<String> getAllLocationAddress();

    @Query(value = "SELECT DISTINCT s.currentLocation.street from Scooter s where s.active = true")
    Set<String> getAllLocationAddressActiveScooters();

    @Query(value = "SELECT MIN(s.batteryCharge) FROM Scooter s")
    int getMinBatteryCharge();

    @Query(value = "SELECT MIN(s.batteryCharge) FROM Scooter s where s.active = true")
    int getMinBatteryChargeActiveScooters();

    @Query(value = "SELECT MAX(s.batteryCharge) FROM Scooter s")
    int getMaxBatteryCharge();

    @Query(value = "SELECT MAX(s.batteryCharge) FROM Scooter s where s.active = true")
    int getMaxBatteryChargeActiveScooters();

    boolean existsByActiveTrue();

}
