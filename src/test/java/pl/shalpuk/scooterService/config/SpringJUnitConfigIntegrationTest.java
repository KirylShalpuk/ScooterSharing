package pl.shalpuk.scooterService.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(SpringJUnitConfigIntegrationTest.Config.class)
@ComponentScan("pl.shalpuk")
public class SpringJUnitConfigIntegrationTest {

    @Configuration
    static class Config {}

}
