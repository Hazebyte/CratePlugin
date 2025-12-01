package com.hazebyte.crate.cratereloaded.util.format;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat extends Format {

    public DateFormat(String message) {
        super(message);
    }

    @Override
    public String format(Object object) {
        if (object instanceof Long) {
            return format((long) object);
        }
        return message;
    }

    public String format(long timestamp) {
        SimpleDateFormat dateFormat = CorePlugin.getPlugin().getSettings().getDateFormat();
        dateFormat.setTimeZone(CorePlugin.getPlugin().getSettings().getServerTimeZone());

        Date date = new Date(timestamp);
        message = message.replace("{date}", dateFormat.format(date));

        return message;
    }
}
