package pl.shalpuk.scooterService;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import pl.shalpuk.scooterService.config.ApplicationConfig;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {

    private final Logger logger = Logger.getLogger(ApplicationInitializer.class);

    @Override
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
//        Properties properties = new Properties();
//        properties.setProperty("log4j.rootLogger", "INFO, stdout, file");
//        properties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
//        properties.setProperty("log4j.appender.stdout.Target", "System.out");
//        properties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
//        properties.setProperty("log4j.appender.stdout.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
//        PropertyConfigurator.configure(properties);

//        try (InputStream inputStream = getClass().getResourceAsStream("/log4j.properties")) {
//            Properties properties = new Properties();
//            properties.load(inputStream);
//            PropertyConfigurator.configure(properties);
//        } catch (Exception ex) {
//            System.out.println("Something was gone wrong");
//        }
//        BasicConfigurator.configure();

        logger.info("Comparing spring application context...");
        long startProcessingMillis = System.currentTimeMillis();

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfig.class);
        context.setServletContext(servletContext);

        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        long endProcessingMillis = System.currentTimeMillis();
        long finalProcessingTime = endProcessingMillis - startProcessingMillis;
        logger.info(String.format("Spring application context was built successfully, load time: %s ms", finalProcessingTime));
    }
}
