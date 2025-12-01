package com.hazebyte.crate.cratereloaded.util.format;

import org.jetbrains.annotations.NotNull;

public abstract class Format {

    protected String message;

    public Format(@NotNull String message) {
        this.message = message;
    }

    public abstract String format(Object object);
}
