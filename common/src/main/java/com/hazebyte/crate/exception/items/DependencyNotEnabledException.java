package com.hazebyte.crate.exception.items;

public class DependencyNotEnabledException extends Exception implements HandledException {
    public DependencyNotEnabledException(String pluginName) {
        super(String.format("Plugin is not enabled: %s", pluginName));
    }
}
