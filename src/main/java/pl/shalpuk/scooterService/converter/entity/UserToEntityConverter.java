package pl.shalpuk.scooterService.converter.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.PaymentInformationDto;
import pl.shalpuk.scooterService.dto.UserDto;
import pl.shalpuk.scooterService.model.PaymentInformation;
import pl.shalpuk.scooterService.model.User;

import java.util.Objects;

@Component
public class UserToEntityConverter implements ToEntityConverter<UserDto, User> {

    private final PaymentInformationToEntityConverter paymentInformationToEntityConverter;

    public UserToEntityConverter(PaymentInformationToEntityConverter paymentInformationToEntityConverter) {
        this.paymentInformationToEntityConverter = paymentInformationToEntityConverter;
    }

    @Override
    public User convertToEntity(UserDto dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity, "id", "paymentInformation");

        PaymentInformationDto paymentInformationDto = dto.getPaymentInformationDto();
        if (Objects.nonNull(paymentInformationDto)) {
            PaymentInformation paymentInformation = paymentInformationToEntityConverter.convertToEntity(paymentInformationDto);
            entity.setPaymentInformation(paymentInformation);
        }

        return entity;
    }
}
