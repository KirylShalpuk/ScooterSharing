package pl.shalpuk.scooterService.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.ScooterToDtoConverter;
import pl.shalpuk.scooterService.converter.entity.ScooterToEntityConverter;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.dto.ScooterSpecificationDto;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.ScooterSortingField;
import pl.shalpuk.scooterService.service.ScooterService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/scooters")
public class ScooterController {

    private final ScooterService scooterService;
    private final ScooterToEntityConverter entityConverter;
    private final ScooterToDtoConverter dtoConverter;

    public ScooterController(ScooterService scooterService,
                             ScooterToEntityConverter entityConverter,
                             ScooterToDtoConverter dtoConverter) {
        this.scooterService = scooterService;
        this.entityConverter = entityConverter;
        this.dtoConverter = dtoConverter;
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{scooterId}")
    public ResponseEntity<?> getScooterById(@PathVariable UUID scooterId) {
        Scooter scooter = scooterService.getScooterById(scooterId);
        return ResponseEntity.ok(dtoConverter.convertToDto(scooter));
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllScootersPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) int page,
            @RequestParam(value = "elements", defaultValue = "20", required = false) @Min(20) @Max(50) int elements,
            @RequestParam(value = "sortDirection", defaultValue = "ASC", required = false) Sort.Direction sortDirection,
            @RequestParam(value = "sortBy", defaultValue = "ADDRESS", required = false) ScooterSortingField sortBy,
            @RequestBody ScooterSpecificationDto specificationDto) {
        PageRequest pageRequest = PageRequest.of(page, elements, sortDirection, sortBy.getSortField());
        Page<Scooter> scooterPage = scooterService.getAllScootersPage(pageRequest, specificationDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(scooterPage));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createScooter(@RequestBody ScooterDto request) {
        Scooter fromDto = entityConverter.convertToEntity(request);
        Scooter createdScooter = scooterService.createScooter(fromDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(createdScooter));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{scooterId}")
    public ResponseEntity<?> updateScooter(@PathVariable UUID scooterId, @RequestBody ScooterDto request) {
        Scooter updatedScooter = scooterService.updateScooter(scooterId, request);
        return ResponseEntity.ok(dtoConverter.convertToDto(updatedScooter));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/{scooterId}")
    public ResponseEntity<?> deleteScooter(@PathVariable UUID scooterId) {
        scooterService.deleteScooterById(scooterId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<?> getScooterFilterProperties() {
        ScooterSpecificationDto response = scooterService.getScooterFilterProperties();
        return ResponseEntity.ok(response);
    }
}
