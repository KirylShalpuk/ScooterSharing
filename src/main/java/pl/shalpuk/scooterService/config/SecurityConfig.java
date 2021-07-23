package pl.shalpuk.scooterService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.shalpuk.scooterService.filter.JWTAuthenticationFilter;

@EnableWebSecurity
@Configuration
@ComponentScan("pl.shalpuk")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationFilter JWTAuthenticationFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JWTAuthenticationFilter jwtAuthenticationFilter,
                          UserDetailsService userDetailsService) {
        this.JWTAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").hasRole("VIEWER")
                .antMatchers(HttpMethod.GET, "/user/*").hasAnyRole("ADMIN", "VIEWER")
                .antMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/*").hasAnyRole("ADMIN", "VIEWER")
                .antMatchers(HttpMethod.DELETE, "/user/*").hasAnyRole("ADMIN")
                .antMatchers("/user/*/activate").hasAnyRole("ADMIN", "VIEWER")
                .antMatchers("/user/*/deactivate", "/user/*/updateRole").hasRole("ADMIN")

                .antMatchers("/role/*").hasRole("ADMIN")

                .antMatchers("/scooter/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/scooter/*").hasAnyRole("ADMIN", "VIEWER")

                .antMatchers("/ride/*").hasAnyRole("ADMIN", "VIEWER")

                .antMatchers("/auth/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(JWTAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
