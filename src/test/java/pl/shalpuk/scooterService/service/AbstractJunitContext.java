package pl.shalpuk.scooterService.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.shalpuk.scooterService.repository.CardRepository;
import pl.shalpuk.scooterService.repository.PaymentInformationRepository;
import pl.shalpuk.scooterService.repository.RideRepository;
import pl.shalpuk.scooterService.repository.RoleRepository;
import pl.shalpuk.scooterService.repository.ScooterRepository;
import pl.shalpuk.scooterService.repository.TariffRepository;
import pl.shalpuk.scooterService.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ComponentScan("pl.shalpuk")
public class AbstractJunitContext {

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

    @org.springframework.context.annotation.Configuration
    public static class ContextConfiguration {
    }
}
