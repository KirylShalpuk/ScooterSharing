package pl.shalpuk.scooterService.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("pl.shalpuk")
@EnableWebMvc
@PropertySource(value = {"classpath:log4j.properties", "classpath:application.properties"})
@EnableScheduling
public class ApplicationConfig implements WebMvcConfigurer {

}
