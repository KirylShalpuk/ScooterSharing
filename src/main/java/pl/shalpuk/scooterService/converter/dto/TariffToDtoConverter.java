package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.TariffDto;
import pl.shalpuk.scooterService.model.Tariff;

@Component
public class TariffToDtoConverter implements ToDtoConverter<Tariff, TariffDto> {

    @Override
    public TariffDto convertToDto(Tariff entity) {
        TariffDto dto = new TariffDto();
        BeanUtils.copyProperties(entity, dto);

        return dto;
    }
}
