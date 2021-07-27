package pl.shalpuk.scooterService.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.RideLocationToDtoConverter;
import pl.shalpuk.scooterService.model.RideLocation;
import pl.shalpuk.scooterService.service.RideLocationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class RideLocationController {

    private final RideLocationService rideLocationService;
    private final RideLocationToDtoConverter dtoConverter;

    public RideLocationController(RideLocationService rideLocationService,
                                  RideLocationToDtoConverter dtoConverter) {
        this.rideLocationService = rideLocationService;
        this.dtoConverter = dtoConverter;
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/rides/locations")
    public ResponseEntity<?> getAllStatisticRidesList(
            @RequestParam(value = "dateFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime dateFrom,
            @RequestParam(value = "dateTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime dateTo) {
        List<RideLocation> ridePage = rideLocationService.getRideLocationStatistic(dateFrom, dateTo);
        return ResponseEntity.ok(dtoConverter.convertToDto(ridePage));
    }

}
