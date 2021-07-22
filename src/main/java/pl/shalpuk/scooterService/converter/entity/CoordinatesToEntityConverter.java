package pl.shalpuk.scooterService.converter.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.CoordinatesDto;
import pl.shalpuk.scooterService.model.Coordinates;

@Component
public class CoordinatesToEntityConverter implements ToEntityConverter<CoordinatesDto, Coordinates> {

    @Override
    public Coordinates convertToEntity(CoordinatesDto dto) {
        Coordinates entity = new Coordinates();
        BeanUtils.copyProperties(dto, entity, "id");

        return entity;
    }
}
