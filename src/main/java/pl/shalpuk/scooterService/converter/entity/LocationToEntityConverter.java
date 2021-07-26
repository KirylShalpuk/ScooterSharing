package pl.shalpuk.scooterService.converter.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.CoordinatesDto;
import pl.shalpuk.scooterService.dto.LocationDto;
import pl.shalpuk.scooterService.model.Coordinates;
import pl.shalpuk.scooterService.model.Location;

import java.util.Objects;

@Component
public class LocationToEntityConverter implements ToEntityConverter<LocationDto, Location> {

    private final CoordinatesToEntityConverter coordinatesToEntityConverter;

    public LocationToEntityConverter(CoordinatesToEntityConverter coordinatesToEntityConverter) {
        this.coordinatesToEntityConverter = coordinatesToEntityConverter;
    }

    @Override
    public Location convertToEntity(LocationDto dto) {
        Location entity = new Location();
        BeanUtils.copyProperties(dto, entity, "id");
        CoordinatesDto coordinatesDto = dto.getCoordinates();
        if (Objects.nonNull(coordinatesDto)) {
            Coordinates coordinates = coordinatesToEntityConverter.convertToEntity(dto.getCoordinates());
            entity.setCoordinates(coordinates);
        }

        return entity;
    }
}
