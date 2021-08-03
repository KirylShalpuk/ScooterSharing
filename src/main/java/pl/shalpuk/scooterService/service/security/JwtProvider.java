package pl.shalpuk.scooterService.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String jwtSecret;

    private final Logger logger;
    private final long ONE_HOUR_MILLIS = 3_600_000L;

    public JwtProvider(Logger logger) {
        this.logger = logger;
    }


    public String generateToken(String login) {
        Date expirationDate = new Date(System.currentTimeMillis() + ONE_HOUR_MILLIS);

        String token = Jwts.builder()
                .setSubject(login)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        logger.info("Token for user with email = {} was created successfully", login);
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            logger.error(String.format("Token expired [%s]", token));
        } catch (Exception ex) {
            logger.error(String.format("Failed validation token [%s]", token));
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
