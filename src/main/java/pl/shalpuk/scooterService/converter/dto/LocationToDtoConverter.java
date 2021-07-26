package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.CoordinatesDto;
import pl.shalpuk.scooterService.dto.LocationDto;
import pl.shalpuk.scooterService.model.Coordinates;
import pl.shalpuk.scooterService.model.Location;

import java.util.Objects;

@Component
public class LocationToDtoConverter implements ToDtoConverter<Location, LocationDto> {

    private final CoordinatesToDtoConverter coordinatesToDtoConverter;

    public LocationToDtoConverter(CoordinatesToDtoConverter coordinatesToDtoConverter) {
        this.coordinatesToDtoConverter = coordinatesToDtoConverter;
    }

    @Override
    public LocationDto convertToDto(Location entity) {
        LocationDto dto = new LocationDto();
        BeanUtils.copyProperties(entity, dto, "coordinates");

        Coordinates coordinates = entity.getCoordinates();

        if (Objects.nonNull(coordinates)) {
            CoordinatesDto coordinatesDto = coordinatesToDtoConverter.convertToDto(coordinates);
            dto.setCoordinates(coordinatesDto);
        }

        return dto;
    }
}
