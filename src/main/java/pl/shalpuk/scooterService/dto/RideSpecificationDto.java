package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class RideSpecificationDto {

    @JsonProperty("userEmails")
    private Set<String> userEmails;

    @JsonProperty("locationAddress")
    private Set<String> locationAddress;

    public Set<String> getUserEmails() {
        return userEmails;
    }

    public void setUserEmails(Set<String> userEmails) {
        this.userEmails = userEmails;
    }

    public Set<String> getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(Set<String> locationAddress) {
        this.locationAddress = locationAddress;
    }
}
