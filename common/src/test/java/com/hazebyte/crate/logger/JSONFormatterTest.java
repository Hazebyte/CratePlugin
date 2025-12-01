package com.hazebyte.crate.logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.logging.Level;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

public class JSONFormatterTest {

    @Test
    public void test() {
        JSONObject object = new JSONObject();
        object.put("key", "value");
        JSONLogRecord record = new JSONLogRecord(Level.FINE, "claim", object);
        JSONFormatter formatter = new JSONFormatter();
        String msg = formatter.format(record);
        assertNotNull(msg);
        assertFalse(msg.isEmpty());
    }
}
