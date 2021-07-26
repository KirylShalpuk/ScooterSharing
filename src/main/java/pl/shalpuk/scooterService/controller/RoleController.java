package pl.shalpuk.scooterService.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.shalpuk.scooterService.converter.dto.RoleToDtoConverter;
import pl.shalpuk.scooterService.dto.RoleDto;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.service.RoleService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleToDtoConverter dtoConverter;

    public RoleController(RoleService roleService,
                          RoleToDtoConverter dtoConverter) {
        this.roleService = roleService;
        this.dtoConverter = dtoConverter;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<RoleDto>> getAllRolesPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) int page,
            @RequestParam(value = "elements", defaultValue = "20", required = false) @Min(20) @Max(50) int elements,
            @RequestParam(value = "sortDirection", defaultValue = "ASC", required = false) Sort.Direction sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, elements, sortDirection, "name");
        Page<Role> rolePage = roleService.getAllRolesPage(pageRequest);
        return ResponseEntity.ok(dtoConverter.convertToDto(rolePage));
    }
}
