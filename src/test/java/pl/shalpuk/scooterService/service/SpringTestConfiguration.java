package pl.shalpuk.scooterService.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("pl.shalpuk")
@TestPropertySource(locations = {"classpath:test.properties"})
public class SpringTestConfiguration {
}
