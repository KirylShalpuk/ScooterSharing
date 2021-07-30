package pl.shalpuk.scooterService.service.security;

import org.springframework.stereotype.Service;
import pl.shalpuk.scooterService.model.JwtToken;
import pl.shalpuk.scooterService.repository.JwtTokenRepository;

import java.util.Optional;

@Service
public class JwtTokenService {

    private final JwtTokenRepository jwtTokenRepository;

    public JwtTokenService(JwtTokenRepository jwtTokenRepository) {
        this.jwtTokenRepository = jwtTokenRepository;
    }

    public boolean isTokenActive(String token) {
        Optional<JwtToken> jwtToken = jwtTokenRepository.findByTokenAndActiveIsTrue(token);
        return jwtToken.isPresent();
    }

}
