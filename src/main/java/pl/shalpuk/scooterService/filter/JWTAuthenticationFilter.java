package pl.shalpuk.scooterService.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import pl.shalpuk.scooterService.dto.ErrorResponse;
import pl.shalpuk.scooterService.service.security.JwtProvider;
import pl.shalpuk.scooterService.service.security.JwtTokenService;
import pl.shalpuk.scooterService.util.AuthContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Primary
public class JWTAuthenticationFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    public JWTAuthenticationFilter(JwtProvider jwtProvider,
                                   UserDetailsService userDetailsService,
                                   JwtTokenService jwtTokenService) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME);

        boolean isTokenValid = Objects.nonNull(token) && jwtProvider.validateToken(token);
        boolean isTokenActive = jwtTokenService.isTokenActive(token);
        if (isTokenValid && isTokenActive) {
            String email = jwtProvider.getLoginFromToken(token);
            UserDetails user = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            AuthContext.setAuthContext(auth);
        }
        chain.doFilter(request, response);
    }

    private void handleExpiredJwtException(HttpServletResponse response, String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), message);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
