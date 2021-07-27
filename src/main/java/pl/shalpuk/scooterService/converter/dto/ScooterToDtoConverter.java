package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.LocationDto;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Scooter;

import java.util.Objects;

@Component
public class ScooterToDtoConverter implements ToDtoConverter<Scooter, ScooterDto> {

    private final LocationToDtoConverter locationToDtoConverter;

    public ScooterToDtoConverter(LocationToDtoConverter locationToDtoConverter) {
        this.locationToDtoConverter = locationToDtoConverter;
    }

    @Override
    public ScooterDto convertToDto(Scooter entity) {
        ScooterDto dto = new ScooterDto();
        BeanUtils.copyProperties(entity, dto, "currentLocation");

        Location location = entity.getCurrentLocation();
        if (Objects.nonNull(location)) {
            LocationDto locationDto = locationToDtoConverter.convertToDto(location);
            dto.setCurrentLocation(locationDto);
        }
        return dto;
    }
}
