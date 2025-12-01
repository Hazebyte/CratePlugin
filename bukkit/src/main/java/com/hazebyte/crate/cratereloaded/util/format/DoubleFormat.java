package com.hazebyte.crate.cratereloaded.util.format;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.text.DecimalFormat;
import org.jetbrains.annotations.NotNull;

public class DoubleFormat extends Format {

    private final String decimalFormat;

    public DoubleFormat(@NotNull String message) {
        super(message);
        decimalFormat = CorePlugin.getPlugin().getSettings().getDecimalFormat();
    }

    @Override
    public String format(Object object) {
        if (object instanceof Double) {
            return format((Double) object);
        }
        return message;
    }

    public String format(@NotNull Double number) {
        message = message.replace(
                "{chance}", String.format("%s%s", new DecimalFormat(decimalFormat).format(number), "%"));
        return message;
    }
}
