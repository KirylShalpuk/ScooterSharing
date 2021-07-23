package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.CoordinatesDto;
import pl.shalpuk.scooterService.model.Coordinates;

@Component
public class CoordinatesToDtoConverter implements ToDtoConverter<Coordinates, CoordinatesDto> {

    @Override
    public CoordinatesDto convertToDto(Coordinates entity) {
        CoordinatesDto dto = new CoordinatesDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
