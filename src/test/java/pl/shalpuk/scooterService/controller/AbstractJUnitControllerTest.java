package pl.shalpuk.scooterService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.shalpuk.scooterService.config.SpringTestConfiguration;
import pl.shalpuk.scooterService.helper.UserTestHelper;
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
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserLocationRepository;
import pl.shalpuk.scooterService.repository.UserRepository;
import pl.shalpuk.scooterService.service.DataLoader;
import pl.shalpuk.scooterService.service.RideLocationService;
import pl.shalpuk.scooterService.service.RideService;
import pl.shalpuk.scooterService.service.RoleService;
import pl.shalpuk.scooterService.service.ScooterService;
import pl.shalpuk.scooterService.service.TariffService;
import pl.shalpuk.scooterService.service.UserService;
import pl.shalpuk.scooterService.service.security.AuthService;
import pl.shalpuk.scooterService.util.AuthContext;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {SpringTestConfiguration.class})
@WebAppConfiguration
@Transactional
public class AbstractJUnitControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Mock
    protected UserService userService;

    @Mock
    protected ScooterService scooterService;

    @Mock
    protected RoleService roleService;

    @Mock
    protected RideService rideService;

    @Mock
    protected TariffService tariffService;

    @Mock
    protected DataLoader dataLoader;

    @Mock
    protected RideLocationService rideLocationService;

    @Mock
    protected AuthService authService;

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected ScooterRepository scooterRepository;

    @Mock
    protected RoleRepository roleRepository;

    @Mock
    protected RideRepository rideRepository;

    @Mock
    protected TariffRepository tariffRepository;

    @Mock
    protected PaymentInformationRepository paymentInformationRepository;

    @Mock
    protected CardRepository cardRepository;

    @Mock
    protected CoordinatesRepository coordinatesRepository;

    @Mock
    protected JwtTokenRepository jwtTokenRepository;

    @Mock
    protected LocationRepository locationRepository;

    @Mock
    protected RideLocationRepository rideLocationRepository;

    @Mock
    protected UserLocationRepository userLocationRepository;

    @PostConstruct
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());

        User user = UserTestHelper.createUser(null);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        AuthContext.setAuthContext(auth);
    }

}
