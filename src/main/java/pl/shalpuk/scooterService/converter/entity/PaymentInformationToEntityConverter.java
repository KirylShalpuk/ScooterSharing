package pl.shalpuk.scooterService.converter.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.shalpuk.scooterService.dto.CardDto;
import pl.shalpuk.scooterService.dto.PaymentInformationDto;
import pl.shalpuk.scooterService.model.Card;
import pl.shalpuk.scooterService.model.PaymentInformation;

import java.util.Set;

@Component
public class PaymentInformationToEntityConverter implements ToEntityConverter<PaymentInformationDto, PaymentInformation> {

    private final CardToEntityConverter cardToEntityConverter;

    public PaymentInformationToEntityConverter(CardToEntityConverter cardToEntityConverter) {
        this.cardToEntityConverter = cardToEntityConverter;
    }

    @Override
    public PaymentInformation convertToEntity(PaymentInformationDto dto) {
        PaymentInformation entity = new PaymentInformation();
        BeanUtils.copyProperties(dto, entity, "id", "cards");

        Set<CardDto> cardDtos = dto.getCards();
        if (!CollectionUtils.isEmpty(cardDtos)) {
            Set<Card> cards = cardToEntityConverter.convertToEntity(dto.getCards());
            entity.setCards(cards);
        }
        return entity;
    }
}
