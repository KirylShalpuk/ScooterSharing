package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.LocationDto;
import pl.shalpuk.scooterService.dto.RideDto;
import pl.shalpuk.scooterService.dto.ShortScooterDto;
import pl.shalpuk.scooterService.dto.ShortTariffDto;
import pl.shalpuk.scooterService.dto.ShortUserDto;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;

import java.util.Objects;

@Component
public class RideToDtoConverter implements ToDtoConverter<Ride, RideDto> {

    private final LocationToDtoConverter locationToDtoConverter;

    public RideToDtoConverter(LocationToDtoConverter locationToDtoConverter) {
        this.locationToDtoConverter = locationToDtoConverter;
    }

    @Override
    public RideDto convertToDto(Ride entity) {
        RideDto dto = new RideDto();
        BeanUtils.copyProperties(entity, dto, "scooter", "currentLocation");

        Scooter scooter = entity.getScooter();

        Location currentLocation = scooter.getCurrentLocation();
        LocationDto scooterLocationDto;
        if (Objects.nonNull(currentLocation)) {
            scooterLocationDto = locationToDtoConverter.convertToDto(scooter.getCurrentLocation());
        } else {
            scooterLocationDto = null;
        }
        ShortScooterDto scooterDto = new ShortScooterDto(scooter.getId(), scooter.getManufacturer(), scooter.getModel(), scooterLocationDto);

        User user = entity.getUser();
        ShortUserDto userDto = new ShortUserDto(user.getId(), user.getEmail());

        Tariff tariff = entity.getTariff();
        ShortTariffDto tariffDto = new ShortTariffDto(tariff.getId(), tariff.getName());

        dto.setScooter(scooterDto);
        dto.setUser(userDto);
        dto.setTariff(tariffDto);

        return dto;
    }
}
