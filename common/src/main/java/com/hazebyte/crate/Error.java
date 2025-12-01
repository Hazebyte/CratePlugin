package com.hazebyte.crate;

import java.util.function.Function;

public class Error {

    public static final Function<String, Error> NULL_OR_EMPTY_ERROR =
            (s) -> new Error("The object is null or empty [%s]", s);

    public static final Function<String, Error> FORMAT_ERROR =
            (s) -> new Error("The string provided has an formatting error [%s]", s);

    private final String message;

    /**
     * Creates a new Error with the given message.
     *
     * @param message the error message
     * @return a new Error instance
     */
    public static Error of(String message) {
        return new Error(message);
    }

    private Error(String message) {
        this.message = message;
    }

    private Error(String message, String... replace) {
        this.message = String.format(message, replace);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
