package pl.shalpuk.scooterService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.shalpuk.scooterService.config.SpringTestConfiguration;
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

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {SpringTestConfiguration.class})
@WebAppConfiguration
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

//    @Mock
//    protected AuthService authService;

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

}
