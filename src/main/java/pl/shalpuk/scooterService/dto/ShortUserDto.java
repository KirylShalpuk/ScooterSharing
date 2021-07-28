package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ShortUserDto {

    @NotNull
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("email")
    private String email;

    public ShortUserDto() {
    }

    public ShortUserDto(UUID id) {
        this.id = id;
    }

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
