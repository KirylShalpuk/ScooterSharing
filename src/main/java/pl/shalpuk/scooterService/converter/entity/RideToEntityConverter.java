package pl.shalpuk.scooterService.converter.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.RideDto;
import pl.shalpuk.scooterService.model.Ride;

@Component
public class RideToEntityConverter implements ToEntityConverter<RideDto, Ride> {

    @Override
    public Ride convertToEntity(RideDto dto) {
        Ride entity = new Ride();
        BeanUtils.copyProperties(dto, entity, "id");
        return entity;
    }
}
