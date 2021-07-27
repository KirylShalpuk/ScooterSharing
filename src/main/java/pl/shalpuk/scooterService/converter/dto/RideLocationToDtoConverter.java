package pl.shalpuk.scooterService.converter.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import pl.shalpuk.scooterService.dto.LocationDto;
import pl.shalpuk.scooterService.dto.RideLocationDto;
import pl.shalpuk.scooterService.model.Location;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideLocation;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.User;

import java.util.Objects;

@Component
public class RideLocationToDtoConverter implements ToDtoConverter<RideLocation, RideLocationDto> {

    private final LocationToDtoConverter locationToDtoConverter;

    public RideLocationToDtoConverter(LocationToDtoConverter locationToDtoConverter) {
        this.locationToDtoConverter = locationToDtoConverter;
    }

    @Override
    public RideLocationDto convertToDto(RideLocation entity) {
        RideLocationDto dto = new RideLocationDto();
        BeanUtils.copyProperties(entity, dto);

        Location location = entity.getLocation();
        if (Objects.nonNull(location)) {
            LocationDto locationDto = locationToDtoConverter.convertToDto(location);
            dto.setLocation(locationDto);
        }

        Ride ride = entity.getRide();
        User user = ride.getUser();
        Tariff tariff = ride.getTariff();
        Scooter scooter = ride.getScooter();

        dto.setRideId(ride.getId());
        dto.setUserId(user.getId());
        dto.setTariffId(tariff.getId());
        dto.setScooterId(scooter.getId());

        return dto;
    }
}
