package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ScooterLocationStatisticDto {

    @NotNull
    @JsonProperty("street")
    private String street;

    @NotNull
    @JsonProperty("building")
    private String building;

    @JsonProperty("scooterStatistic")
    private List<ScooterStatisticDto> scooterStatisticDtos;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public List<ScooterStatisticDto> getScooterStatisticDtos() {
        return scooterStatisticDtos;
    }

    public void setScooterStatisticDtos(List<ScooterStatisticDto> scooterStatisticDtos) {
        this.scooterStatisticDtos = scooterStatisticDtos;
    }
}
