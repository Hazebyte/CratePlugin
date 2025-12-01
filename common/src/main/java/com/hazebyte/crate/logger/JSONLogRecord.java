package com.hazebyte.crate.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.json.simple.JSONObject;

public class JSONLogRecord extends LogRecord {

    private final JSONObject jsonObject;

    public JSONLogRecord(Level level, String msg, JSONObject jsonObject) {
        super(level, msg);
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
