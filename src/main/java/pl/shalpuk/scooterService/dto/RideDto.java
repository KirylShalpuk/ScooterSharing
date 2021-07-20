package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.shalpuk.scooterService.model.RideStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class RideDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("startRideTime")
    private LocalDateTime startRideTime;

    @JsonProperty("endRideTime")
    private LocalDateTime endRideTime;

    @JsonProperty("rideStatus")
    private RideStatus rideStatus;

    @JsonProperty("userId")
    private UUID userId;

    @JsonProperty("scooterId")
    private UUID scooterId;

    @JsonProperty("tariffId")
    private UUID tariffId;

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

    public LocalDateTime getEndRideTime() {
        return endRideTime;
    }

    public void setEndRideTime(LocalDateTime endRideTime) {
        this.endRideTime = endRideTime;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getScooterId() {
        return scooterId;
    }

    public void setScooterId(UUID scooterId) {
        this.scooterId = scooterId;
    }

    public UUID getTariffId() {
        return tariffId;
    }

    public void setTariffId(UUID tariffId) {
        this.tariffId = tariffId;
    }
}
