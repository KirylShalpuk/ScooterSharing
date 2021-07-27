package pl.shalpuk.scooterService.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.TariffToDtoConverter;
import pl.shalpuk.scooterService.dto.TariffDto;
import pl.shalpuk.scooterService.model.Tariff;
import pl.shalpuk.scooterService.model.TariffSortingField;
import pl.shalpuk.scooterService.service.TariffService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/tariffs")
@Validated
public class TariffController {

    private final TariffService tariffService;
    private final TariffToDtoConverter dtoConverter;

    public TariffController(TariffService tariffService,
                            TariffToDtoConverter dtoConverter) {
        this.tariffService = tariffService;
        this.dtoConverter = dtoConverter;
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping("{tariffId}")
    public ResponseEntity<?> getTariffById(@PathVariable UUID tariffId) {
        Tariff tariff = tariffService.getTariffById(tariffId);
        return ResponseEntity.ok(dtoConverter.convertToDto(tariff));
    }

    @PreAuthorize("hasAnyRole('VIEWER', 'ADMIN', 'SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<TariffDto>> getAllTariffsPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) int page,
            @RequestParam(value = "elements", defaultValue = "20", required = false) @Min(20) @Max(50) int elements,
            @RequestParam(value = "sortDirection", defaultValue = "ASC", required = false) Sort.Direction sortDirection,
            @RequestParam(value = "sortBy", defaultValue = "COSTS", required = false) TariffSortingField sortBy) {
        PageRequest pageRequest = PageRequest.of(page, elements, sortDirection, sortBy.getSortField());
        Page<Tariff> tariffsPage = tariffService.getAllTariffsPage(pageRequest);
        return ResponseEntity.ok(dtoConverter.convertToDto(tariffsPage));
    }
}
