package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.JwtTokenDto;
import pl.shalpuk.scooterService.model.JwtToken;

@Component
public class JwtTokenToDtoConverter implements ToDtoConverter<JwtToken, JwtTokenDto> {

    @Override
    public JwtTokenDto convertToDto(JwtToken entity) {
        JwtTokenDto dto = new JwtTokenDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
