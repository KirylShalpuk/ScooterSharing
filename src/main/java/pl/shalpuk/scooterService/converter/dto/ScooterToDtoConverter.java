package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.model.Scooter;

@Component
public class ScooterToDtoConverter implements ToDtoConverter<Scooter, ScooterDto> {

    @Override
    public ScooterDto convertToDto(Scooter entity) {
        ScooterDto dto = new ScooterDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
