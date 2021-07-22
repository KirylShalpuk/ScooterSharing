package pl.shalpuk.scooterService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class LocationDto {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("address")
    private String address;

    @JsonProperty("building")
    private String building;

    @JsonProperty("coordinates")
    private CoordinatesDto coordinatesDto;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public CoordinatesDto getCoordinatesDto() {
        return coordinatesDto;
    }

    public void setCoordinatesDto(CoordinatesDto coordinatesDto) {
        this.coordinatesDto = coordinatesDto;
    }
}
