package pl.shalpuk.scooterService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.shalpuk.scooterService.converter.dto.ScooterToDtoConverter;
import pl.shalpuk.scooterService.converter.entity.ScooterToEntityConverter;
import pl.shalpuk.scooterService.dto.ScooterDto;
import pl.shalpuk.scooterService.model.Scooter;
import pl.shalpuk.scooterService.service.ScooterService;

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
