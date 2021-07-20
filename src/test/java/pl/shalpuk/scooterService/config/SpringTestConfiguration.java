package pl.shalpuk.scooterService.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@ComponentScan("pl.shalpuk")
@TestPropertySource(locations = {"classpath:application.properties"})
public class SpringTestConfiguration {
}
