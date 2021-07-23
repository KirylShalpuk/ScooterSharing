package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.shalpuk.scooterService.model.RideStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShortRideDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("startRideTime")
    private LocalDateTime startRideTime;

    @JsonProperty("rideStatus")
    private RideStatus rideStatus;

    @JsonProperty("user")
    private ShortUserDto user;

    @JsonProperty("scooter")
    private ShortScooterDto scooter;

    @JsonProperty("tariff")
    private ShortTariffDto tariff;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getStartRideTime() {
        return startRideTime;
    }

    public void setStartRideTime(LocalDateTime startRideTime) {
        this.startRideTime = startRideTime;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public ShortUserDto getUser() {
        return user;
    }

    public void setUser(ShortUserDto user) {
        this.user = user;
    }

    public ShortScooterDto getScooter() {
        return scooter;
    }

    public void setScooter(ShortScooterDto scooter) {
        this.scooter = scooter;
    }

    public ShortTariffDto getTariff() {
        return tariff;
    }

    public void setTariff(ShortTariffDto tariff) {
        this.tariff = tariff;
    }
}
