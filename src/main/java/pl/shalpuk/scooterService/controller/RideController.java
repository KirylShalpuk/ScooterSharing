package pl.shalpuk.scooterService.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
import pl.shalpuk.scooterService.dto.RideSpecificationDto;
import pl.shalpuk.scooterService.model.Ride;
import pl.shalpuk.scooterService.model.RideSortingField;
import pl.shalpuk.scooterService.service.RideService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/rides")
@Validated
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

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createRide(@RequestBody @Valid RideDto request) {
        Ride fromDto = entityConverter.convertToEntity(request);
        Ride createdRide = rideService.createRide(
                request.getUser().getId(),
                request.getScooter().getId(),
                request.getTariff().getId(), fromDto
        );
        return ResponseEntity.ok(dtoConverter.convertToDto(createdRide));
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{rideId}/finish")
    public ResponseEntity<?> finishRide(@PathVariable UUID rideId) {
        Ride finishedRide = rideService.finishRide(rideId);
        return ResponseEntity.ok(dtoConverter.convertToDto(finishedRide));
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{rideId}/complain")
    public ResponseEntity<?> complainAboutRide(@PathVariable UUID rideId) {
        Ride updatedRide = rideService.complainAboutRide(rideId);
        return ResponseEntity.ok(dtoConverter.convertToDto(updatedRide));
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{rideId}")
    public ResponseEntity<?> getRideById(@PathVariable UUID rideId) {
        Ride ride = rideService.getRideById(rideId);
        return ResponseEntity.ok(dtoConverter.convertToDto(ride));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllRidesPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) int page,
            @RequestParam(value = "elements", defaultValue = "20", required = false) @Min(20) @Max(50) int elements,
            @RequestParam(value = "sortDirection", defaultValue = "ASC", required = false) Sort.Direction sortDirection,
            @RequestParam(value = "sortBy", defaultValue = "EMAIL", required = false) RideSortingField sortBy,
            @RequestParam(value = "search", defaultValue = "", required = false) @Min(2) String search,
            @RequestBody RideSpecificationDto specificationDto) {
        PageRequest pageRequest = PageRequest.of(page, elements, sortDirection, sortBy.getSortField());
        Page<Ride> ridePage = rideService.getAllRidesPage(pageRequest, search, specificationDto);
        return ResponseEntity.ok(shortRideToDtoConverter.convertToDto(ridePage));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<?> getRideFilterProperties() {
        return ResponseEntity.ok(rideService.getRideFilterProperties());
    }
}
