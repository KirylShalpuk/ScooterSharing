package pl.shalpuk.scooterService.service.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.dto.AuthDto;
import pl.shalpuk.scooterService.dto.JwtTokenDto;
import pl.shalpuk.scooterService.exception.LoginException;
import pl.shalpuk.scooterService.model.JwtToken;
import pl.shalpuk.scooterService.model.User;
import pl.shalpuk.scooterService.repository.JwtTokenRepository;
import pl.shalpuk.scooterService.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class AuthService {

    private final Logger logger;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final JwtTokenRepository jwtTokenRepository;

    public AuthService(Logger logger,
                       PasswordEncoder passwordEncoder,
                       UserService userService,
                       JwtProvider jwtProvider,
                       JwtTokenRepository jwtTokenRepository) {
        this.logger = logger;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @Transactional
    public JwtToken loginUser(AuthDto request) {
        User user = userService.getActiveUserByEmail(request.getLogin());

        if (!isPasswordsMatch(request, user)) {
            throw new LoginException("Passwords are not equal");
        }

        Optional<JwtToken> jwtTokenOptional = jwtTokenRepository.findByUserIdAndActiveIsTrue(user.getId());
        if (jwtTokenOptional.isPresent()) {
            String token = jwtTokenOptional.get().getToken();
            boolean isValid = false;
            try {
                isValid = jwtProvider.validateToken(token);
            } catch (ExpiredJwtException exception) {
                logger.info(String.format("Token expired [%s]", token));
            }

            if (isValid) {
                return jwtTokenOptional.get();
            } else {
                JwtToken oldToken = jwtTokenOptional.get();
                oldToken.setActive(false);
                jwtTokenRepository.save(oldToken);
                return jwtTokenRepository.save(generateNewToken(user));
            }
        } else {
            return jwtTokenRepository.save(generateNewToken(user));
        }
    }

    public void logoutUser(JwtTokenDto logoutRequest) {
        JwtToken jwtToken = jwtTokenRepository.findByTokenAndActiveIsTrue(logoutRequest.getToken())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Token [%s] is not found", logoutRequest.getToken())));

        jwtToken.setActive(false);
        jwtTokenRepository.save(jwtToken);
        logger.info(String.format("Token [%s] was deactivated successfully", logoutRequest));
    }

    private JwtToken generateNewToken(User user) {
        String token = jwtProvider.generateToken(user.getEmail());

        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setUser(user);
        jwtToken.setCreationDate(LocalDateTime.now());
        jwtToken.setActive(true);

        return jwtToken;
    }

    private boolean isPasswordsMatch(AuthDto request, User user) {
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
