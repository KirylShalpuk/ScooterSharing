package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.shalpuk.scooterService.model.PaymentStatus;
import pl.shalpuk.scooterService.model.RideStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class RideDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("startRideTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime startRideTime;

    @JsonProperty("endRideTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime endRideTime;

    @JsonProperty("rideStatus")
    private RideStatus rideStatus;

    @NotNull(message = "User can not be null")
    @JsonProperty("user")
    private ShortUserDto user;

    @NotNull(message = "Scooter can not be null")
    @JsonProperty("scooter")
    private ShortScooterDto scooter;

    @NotNull(message = "Tariff can not be null")
    @JsonProperty("tariff")
    private ShortTariffDto tariff;

    @JsonProperty("paymentStatus")
    private PaymentStatus paymentStatus;

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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
