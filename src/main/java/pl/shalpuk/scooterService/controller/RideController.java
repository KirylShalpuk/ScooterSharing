package pl.shalpuk.scooterService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.RideToDtoConverter;
import pl.shalpuk.scooterService.converter.entity.RideToEntityConverter;
import pl.shalpuk.scooterService.dto.RideDto;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.service.RideService;

import java.util.UUID;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;
    private final RideToDtoConverter dtoConverter;
    private final RideToEntityConverter entityConverter;

    public RideController(RideService rideService, RideToDtoConverter dtoConverter, RideToEntityConverter entityConverter) {
        this.rideService = rideService;
        this.dtoConverter = dtoConverter;
        this.entityConverter = entityConverter;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRide(@RequestBody RideDto request) {
        Ride fromDto = entityConverter.convertToEntity(request);
        Ride createdRide = rideService.createRide(request.getUserId(), request.getScooterId(), request.getTariffId(), fromDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(createdRide));
    }

    @PutMapping("/{rideId}/finish")
    public ResponseEntity<?> finishRide(@PathVariable UUID rideId) {
        Ride finishedRide = rideService.finishRide(rideId);
        return ResponseEntity.ok(dtoConverter.convertToDto(finishedRide));
    }

    @PutMapping("/{rideId}/complain")
    public ResponseEntity<?> complainAboutRide(@PathVariable UUID rideId) {
        Ride updatedRide = rideService.complainAboutRide(rideId);
        return ResponseEntity.ok(dtoConverter.convertToDto(updatedRide));
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<?> getRideById(@PathVariable UUID rideId) {
        Ride ride = rideService.getRideById(rideId);
        return ResponseEntity.ok(dtoConverter.convertToDto(ride));
    }
}
