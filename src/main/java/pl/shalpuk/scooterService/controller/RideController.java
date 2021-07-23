package pl.shalpuk.scooterService.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.RideToDtoConverter;
import pl.shalpuk.scooterService.converter.dto.ShortRideToDtoConverter;
import pl.shalpuk.scooterService.converter.entity.RideToEntityConverter;
import pl.shalpuk.scooterService.dto.RideDto;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideSortingField;
import pl.shalpuk.scooterService.service.RideService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;
    private final RideToDtoConverter dtoConverter;
    private final RideToEntityConverter entityConverter;
    private final ShortRideToDtoConverter shortRideToDtoConverter;

    public RideController(RideService rideService,
                          RideToDtoConverter dtoConverter,
                          RideToEntityConverter entityConverter,
                          ShortRideToDtoConverter shortRideToDtoConverter) {
        this.rideService = rideService;
        this.dtoConverter = dtoConverter;
        this.entityConverter = entityConverter;
        this.shortRideToDtoConverter = shortRideToDtoConverter;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRide(@RequestBody RideDto request) {
        Ride fromDto = entityConverter.convertToEntity(request);
        Ride createdRide = rideService.createRide(
                request.getUser().getId(),
                request.getScooter().getId(),
                request.getTariff().getId(), fromDto
        );
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

    @GetMapping("/")
    public ResponseEntity<?> getAllRidesPage(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "elements", defaultValue = "0") @Min(20) @Max(50) int elements,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(value = "sortBy", defaultValue = "EMAIL") RideSortingField sortBy,
            @RequestParam(value = "search", defaultValue = "") @Min(2) String search) {
        PageRequest pageRequest = PageRequest.of(page, elements, sortDirection, sortBy.getSortField());
        Page<Ride> ridePage = rideService.getAllRidesPage(pageRequest, search);
        return ResponseEntity.ok(shortRideToDtoConverter.convertToDto(ridePage));
    }
}
