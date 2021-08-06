package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.shalpuk.scooterService.config.SpringTestConfiguration;
import pl.shalpuk.scooterService.model.DefaultRoles;
import pl.shalpuk.scooterService.model.Role;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.CardRepository;
import pl.shalpuk.scooterService.repository.CoordinatesRepository;
import pl.shalpuk.scooterService.repository.JwtTokenRepository;
import pl.shalpuk.scooterService.repository.LocationRepository;
import pl.shalpuk.scooterService.repository.PaymentInformationRepository;
import pl.shalpuk.scooterService.repository.RideLocationRepository;
import pl.shalpuk.scooterService.repository.RideRepository;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.ScooterStatisticRepository;
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserLocationRepository;
import pl.shalpuk.scooterService.repository.UserRepository;
import pl.shalpuk.scooterService.service.security.AuthService;
import pl.shalpuk.scooterService.util.AuthContext;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestConfiguration.class})
@WebAppConfiguration
@Transactional
public class AbstractIntegrationServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected ScooterService scooterService;

    @Autowired
    protected RoleService roleService;

    @Autowired
    protected RideService rideService;

    @Autowired
    protected TariffService tariffService;

    @Autowired
    protected DataLoader dataLoader;

    @Autowired
    protected RideLocationService rideLocationService;

    @Autowired
    protected AuthService authService;



    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ScooterRepository scooterRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected RideRepository rideRepository;

    @Autowired
    protected TariffRepository tariffRepository;

    @Autowired
    protected PaymentInformationRepository paymentInformationRepository;

    @Autowired
    protected CardRepository cardRepository;

    @Autowired
    protected CoordinatesRepository coordinatesRepository;

    @Autowired
    protected JwtTokenRepository jwtTokenRepository;

    @Autowired
    protected LocationRepository locationRepository;

    @Autowired
    protected RideLocationRepository rideLocationRepository;

    @Autowired
    protected UserLocationRepository userLocationRepository;

    @Autowired
    protected ScooterStatisticRepository scooterStatisticRepository;

    @PostConstruct
    public void init() {
        Role adminRole = roleRepository.findRoleByName(DefaultRoles.ADMIN.toString()).orElse(null);
        User admin = userRepository.findAllByRole(adminRole).stream().findFirst().get();

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());

        AuthContext.setAuthContext(auth);
    }

}
