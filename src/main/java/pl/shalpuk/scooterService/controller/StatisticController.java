package pl.shalpuk.scooterService.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.RideLocationToDtoConverter;
import pl.shalpuk.scooterService.dto.ScooterLocationStatisticDto;
import pl.shalpuk.scooterService.model.RideLocation;
import pl.shalpuk.scooterService.service.RideLocationService;
import pl.shalpuk.scooterService.service.ScooterService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

    private final RideLocationService rideLocationService;
    private final ScooterService scooterService;
    private final RideLocationToDtoConverter rideLocationToDtoConverter;

    public StatisticController(RideLocationService rideLocationService,
                               ScooterService scooterService,
                               RideLocationToDtoConverter rideLocationToDtoConverter) {
        this.rideLocationService = rideLocationService;
        this.scooterService = scooterService;
        this.rideLocationToDtoConverter = rideLocationToDtoConverter;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/rides/locations")
    public ResponseEntity<?> getAllRideStatistics(
            @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime dateFrom,
            @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime dateTo) {
        List<RideLocation> rideList = rideLocationService.getAllRideStatistics(dateFrom, dateTo);
        return ResponseEntity.ok(rideLocationToDtoConverter.convertToDto(rideList));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/scooter/locations")
    public ResponseEntity<?> getAllFreeActiveScooters(@RequestBody @Valid ScooterLocationStatisticDto statisticDto) {
        return ResponseEntity.ok(scooterService.getAllFreeActiveScooters(statisticDto));
    }

}
