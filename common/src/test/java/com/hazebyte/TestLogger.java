package com.hazebyte;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TestLogger {

    private static Logger logger = Logger.getGlobal();

    private static String LOGGING_IDENTIFIER = "ENABLE_CONSOLE_LOGGING";

    private static String ENABLE_FLAG = "1";

    private static boolean enableLogging = false;

    static {
        String envValue = System.getenv(LOGGING_IDENTIFIER);
        if (envValue != null && envValue.equals(ENABLE_FLAG)) {
            enableLogging = true;
        }
    }

    public static Logger getLogger() {
        if (!enableLogging) {
            LogManager.getLogManager().reset(); // disables logging
        }
        if (logger == null) {
            logger = Logger.getGlobal();
        }
        return logger;
    }
}
