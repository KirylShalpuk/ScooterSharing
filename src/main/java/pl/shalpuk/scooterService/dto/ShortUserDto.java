package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ShortUserDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("email")
    private String email;

    public ShortUserDto(UUID id, String email) {
        this.id = id;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
