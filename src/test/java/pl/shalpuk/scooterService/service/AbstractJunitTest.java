package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.shalpuk.scooterService.config.SpringTestConfiguration;
import pl.shalpuk.scooterService.repository.CardRepository;
import pl.shalpuk.scooterService.repository.PaymentInformationRepository;
import pl.shalpuk.scooterService.repository.RideRepository;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserRepository;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestConfiguration.class})
@WebAppConfiguration
@Transactional
public class AbstractJunitTest {

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

}
