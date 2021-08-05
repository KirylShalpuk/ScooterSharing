package pl.shalpuk.scooterService.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    private final DataSource dataSource;

    @Value("${liquibase.change-log.path}")
    private String changelogPath;

    @Value("${liquibase.context}")
    private String liquibaseContext;

    @Value("${liquibase.enable}")
    private boolean enable;

    public LiquibaseConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SpringLiquibase configureLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changelogPath);
        liquibase.setDataSource(dataSource);
        liquibase.setContexts(liquibaseContext);
        liquibase.setShouldRun(enable);
        return liquibase;
    }
}
