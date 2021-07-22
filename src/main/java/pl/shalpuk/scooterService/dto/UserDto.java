package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import pl.shalpuk.scooterService.util.PasswordDeserialization;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

public class UserDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @NotBlank
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @NotBlank
    @JsonDeserialize(using = PasswordDeserialization.class)
    @JsonProperty("password")
    private String password;

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    @Size(max = 500)
    @JsonProperty("photoUrl")
    private String photoUrl;

    @JsonProperty("paymentInformation")
    private PaymentInformationDto paymentInformationDto;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("location")
    private LocationDto userLocation;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public PaymentInformationDto getPaymentInformationDto() {
        return paymentInformationDto;
    }

    public void setPaymentInformationDto(PaymentInformationDto paymentInformationDto) {
        this.paymentInformationDto = paymentInformationDto;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocationDto getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(LocationDto userLocation) {
        this.userLocation = userLocation;
    }
}
