package pl.shalpuk.scooterService.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.repository.RoleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class RoleService {

    private final Logger logger;
    private final RoleRepository roleRepository;

    public RoleService(Logger logger, RoleRepository roleRepository) {
        this.logger = logger;
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Role with name %s is not found", roleName)));
    }
}
