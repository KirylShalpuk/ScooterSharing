package pl.shalpuk.scooterService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import pl.shalpuk.scooterService.config.ApplicationConfig;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {

    private final Logger logger = LogManager.getLogger(ApplicationInitializer.class);

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        logger.info("Comparing spring application context...");
        long startProcessingMillis = System.currentTimeMillis();

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfig.class);
        context.setServletContext(servletContext);

        ServletRegistration.Dynamic servlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        long endProcessingMillis = System.currentTimeMillis();
        long finalProcessingTime = endProcessingMillis - startProcessingMillis;
        logger.info("Spring application context was built " +
                "successfully, load time: {} ms", finalProcessingTime);
    }
}
