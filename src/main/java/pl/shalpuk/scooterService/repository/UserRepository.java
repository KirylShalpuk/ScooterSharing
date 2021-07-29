package pl.shalpuk.scooterService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> getUserByEmailAndActiveIsTrue(String email);

    boolean existsByPhoneNumberOrEmail(String phoneNumber, String email);

    Page<User> getAllByEmailIgnoreCaseContaining(String email, Pageable pageable);

    List<User> findAllByRole(Role role);
}
