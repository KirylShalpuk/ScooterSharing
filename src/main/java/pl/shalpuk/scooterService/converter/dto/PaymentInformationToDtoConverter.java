package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pl.shalpuk.scooterService.dto.CardDto;
import pl.shalpuk.scooterService.dto.PaymentInformationDto;
import pl.shalpuk.scooterService.model.Card;
import pl.shalpuk.scooterService.model.PaymentInformation;

import java.util.Set;

@Component
public class PaymentInformationToDtoConverter implements ToDtoConverter<PaymentInformation, PaymentInformationDto> {

    private final CardToDtoConverter cardToDtoConverter;

    public PaymentInformationToDtoConverter(CardToDtoConverter cardToDtoConverter) {
        this.cardToDtoConverter = cardToDtoConverter;
    }

    @Override
    public PaymentInformationDto convertToDto(PaymentInformation entity) {
        PaymentInformationDto dto = new PaymentInformationDto();
        BeanUtils.copyProperties(entity, dto, "cards");

        Set<Card> cards = entity.getCards();
        if (!CollectionUtils.isEmpty(cards)) {
            Set<CardDto> cardDtos = cardToDtoConverter.convertToDto(entity.getCards());
            dto.setCards(cardDtos);
        }

        return dto;
    }
}
