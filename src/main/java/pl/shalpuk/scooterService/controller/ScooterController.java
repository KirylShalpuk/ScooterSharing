package pl.shalpuk.scooterService.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.model.UserSortingField;
import pl.shalpuk.scooterService.service.ScooterService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/scooter")
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

    @GetMapping("/{scooterId}")
    public ResponseEntity<?> getScooterById(@PathVariable UUID scooterId) {
        Scooter scooter = scooterService.getScooterById(scooterId);
        return ResponseEntity.ok(dtoConverter.convertToDto(scooter));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllScootersPage(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "elements", defaultValue = "0") @Min(20) @Max(50) int elements,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection,
            @RequestParam(value = "sortBy", defaultValue = "EMAIL") UserSortingField sortBy,
            @RequestParam(value = "search", defaultValue = "") @Min(2) String search) {
        PageRequest pageRequest = PageRequest.of(page, elements, sortDirection, sortBy.getSortField());
        Scooter scooter = scooterService.getAllScootersPage(pageRequest);
        return ResponseEntity.ok(dtoConverter.convertToDto(scooter));
    }

    @PostMapping("/")
    public ResponseEntity<?> createScooter(@RequestBody ScooterDto request) {
        Scooter fromDto = entityConverter.convertToEntity(request);
        Scooter createdScooter = scooterService.createScooter(fromDto);
        return ResponseEntity.ok(dtoConverter.convertToDto(createdScooter));
    }

    @PutMapping("/{scooterId}")
    public ResponseEntity<?> updateScooter(@PathVariable UUID scooterId, @RequestBody ScooterDto request) {
        Scooter updatedScooter = scooterService.updateScooter(scooterId, request);
        return ResponseEntity.ok(dtoConverter.convertToDto(updatedScooter));
    }

    @DeleteMapping("/{scooterId}")
    public ResponseEntity<?> deleteScooter(@PathVariable UUID scooterId) {
        scooterService.deleteScooterById(scooterId);
        return ResponseEntity.ok().build();
    }
}
