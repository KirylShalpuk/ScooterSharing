package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.PaymentInformationDto;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.model.PaymentInformation;
import pl.shalpuk.scooterService.model.User;

import java.util.Objects;

@Component
public class UserToDtoConverter implements ToDtoConverter<User, UserDto> {

    private final PaymentInformationToDtoConverter paymentInformationToDtoConverter;

    public UserToDtoConverter(PaymentInformationToDtoConverter paymentInformationToDtoConverter) {
        this.paymentInformationToDtoConverter = paymentInformationToDtoConverter;
    }

    @Override
    public UserDto convertToDto(User entity) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entity, dto, "password", "paymentInformation");

        PaymentInformation paymentInformation = entity.getPaymentInformation();
        if (Objects.nonNull(paymentInformation)) {
            PaymentInformationDto paymentInformationDto = paymentInformationToDtoConverter.convertToDto(paymentInformation);
            dto.setPaymentInformationDto(paymentInformationDto);
        }
        return dto;
    }
}
