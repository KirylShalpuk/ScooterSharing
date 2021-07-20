package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.RideDto;
import pl.shalpuk.scooterService.model.Ride;

@Component
public class RideToDtoConverter implements ToDtoConverter<Ride, RideDto> {

    @Override
    public RideDto convertToDto(Ride entity) {
        RideDto dto = new RideDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setScooterId(entity.getScooter().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setTariffId(entity.getTariff().getId());
        return dto;
    }
}
