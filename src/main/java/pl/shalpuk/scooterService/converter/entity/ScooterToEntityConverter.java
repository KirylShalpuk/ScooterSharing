package pl.shalpuk.scooterService.converter.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.model.Scooter;

@Component
public class ScooterToEntityConverter implements ToEntityConverter<ScooterDto, Scooter> {

    @Override
    public Scooter convertToEntity(ScooterDto dto) {
        Scooter entity = new Scooter();
        BeanUtils.copyProperties(dto, entity, "id");
        return entity;
    }
}
