package pl.shalpuk.scooterService.converter.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.CardDto;
import pl.shalpuk.scooterService.model.Card;

@Component
public class CardToEntityConverter implements ToEntityConverter<CardDto, Card> {

    @Override
    public Card convertToEntity(CardDto dto) {
        Card entity = new Card();
        BeanUtils.copyProperties(dto, entity, "id");
        return entity;
    }
}
