package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserActivationDto {

    @JsonProperty("accessCode")
    private String accessCode;

    @JsonProperty("accessStatus")
    private boolean accessStatus;

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public boolean isAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(boolean accessStatus) {
        this.accessStatus = accessStatus;
    }
}
