package com.hazebyte.crate.logger;

import java.util.logging.Formatter;
import java.util.logging.XMLFormatter;

public class FormatterFactory {

    public static Formatter create(String name) {
        switch (name.toLowerCase()) {
            case "json":
                return new JSONFormatter();
            case "xml":
            default:
                return new XMLFormatter();
        }
    }
}
