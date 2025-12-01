package com.hazebyte.crate.exception;

import com.hazebyte.crate.exception.items.HandledException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {

    private final Logger logger;

    public ExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    public void handle(Exception ex) {
        logger.log(Level.FINEST, "Caught exception", ex);
        if (ex instanceof HandledException) {
            logger.info(ex.getMessage());
        } else {
            logger.log(Level.SEVERE, "Uncaught exception. Please report this to the author...", ex);
        }
    }
}
