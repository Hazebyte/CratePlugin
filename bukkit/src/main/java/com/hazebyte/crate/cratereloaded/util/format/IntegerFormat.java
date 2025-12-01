package com.hazebyte.crate.cratereloaded.util.format;

import org.jetbrains.annotations.NotNull;

public class IntegerFormat extends Format {

    public IntegerFormat(@NotNull String message) {
        super(message);
    }

    @Override
    public String format(Object object) {
        if (object instanceof Integer) {
            return format((Integer) object);
        }
        return message;
    }

    public String format(@NotNull Integer number) {
        message = message.replace("{number}", Integer.toString(number));
        return message;
    }
}
