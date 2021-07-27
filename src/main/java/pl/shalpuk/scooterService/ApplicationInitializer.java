package pl.shalpuk.scooterService;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import pl.shalpuk.scooterService.config.ApplicationConfig;
import pl.shalpuk.scooterService.util.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {

    private final Logger logger = Logger.getLogger(ApplicationInitializer.class);

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        LogUtil.logInfo(logger, "Comparing spring application context...");
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
        LogUtil.logInfo(logger, String.format("Spring application context was built " +
                "successfully, load time: %s ms", finalProcessingTime));
    }
}
