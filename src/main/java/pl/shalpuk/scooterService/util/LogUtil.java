package pl.shalpuk.scooterService.util;

import org.apache.log4j.Logger;

public class LogUtil {

    public static void logInfo(Logger logger, String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }
}
