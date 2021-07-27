package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RideLocationDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("rideId")
    private UUID rideId;

    @JsonProperty("userId")
    private UUID userId;

    @JsonProperty("scooterId")
    private UUID scooterId;

    @JsonProperty("tariffId")
    private UUID tariffId;

    @JsonProperty("location")
    private LocationDto location;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRideId() {
        return rideId;
    }

    public void setRideId(UUID rideId) {
        this.rideId = rideId;
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

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }
}
