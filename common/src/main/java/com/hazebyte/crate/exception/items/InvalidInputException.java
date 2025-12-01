package com.hazebyte.crate.exception.items;

public class InvalidInputException extends Exception implements HandledException {
    public InvalidInputException(String message) {
        super(String.format("Invalid input provided: %s", message));
    }
}
