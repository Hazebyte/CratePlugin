package com.hazebyte.crate.exception.items;

import com.hazebyte.crate.Error;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends Exception implements HandledException {

    private final List<Error> errors;

    public ValidationException(List<Error> errors) {
        // The message is comma separated errors.
        super(errors.stream().map(err -> err.getMessage()).collect(Collectors.joining(", ")));
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
