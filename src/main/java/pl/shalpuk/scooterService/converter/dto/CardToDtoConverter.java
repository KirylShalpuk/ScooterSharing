package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.CardDto;
import pl.shalpuk.scooterService.model.Card;

@Component
public class CardToDtoConverter implements ToDtoConverter<Card, CardDto> {

    @Override
    public CardDto convertToDto(Card entity) {
        CardDto dto = new CardDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
