package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ShortTariffDto {

    @NotNull
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    public ShortTariffDto() {
    }

    public ShortTariffDto(UUID id) {
        this.id = id;
    }

    public ShortTariffDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
