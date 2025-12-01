package com.hazebyte.crate.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        JSONObject rootObject = new JSONObject();
        rootObject.put("level", record.getLevel());
        rootObject.put("sequence", record.getSequenceNumber());
        rootObject.put("logger", record.getLoggerName());
        rootObject.put("class", record.getSourceClassName());
        rootObject.put("method", record.getSourceMethodName());
        rootObject.put("thread", record.getThreadID());
        rootObject.put("millis", record.getMillis());
        rootObject.put("message", record.getMessage());

        if (record.getThrown() != null) {
            Throwable th = record.getThrown();
            JSONObject exceptionObject = new JSONObject();
            JSONArray traceArray = new JSONArray();
            for (StackTraceElement trace : th.getStackTrace()) {
                JSONObject traceObject = new JSONObject();
                traceObject.put("class", trace.getClassName());
                traceObject.put("method", trace.getMethodName());
                if (trace.getLineNumber() >= 0) {
                    traceObject.put("line", trace.getLineNumber());
                }
                traceArray.add(traceObject);
            }
            exceptionObject.put("message", th.getMessage());
            exceptionObject.put("frame", traceArray);
            rootObject.put("exception", exceptionObject);
        }

        if (record instanceof JSONLogRecord) {
            JSONLogRecord jsonLogRecord = (JSONLogRecord) record;
            JSONObject jsonObject = jsonLogRecord.getJsonObject();
            rootObject.put("jsonObject", jsonObject.toJSONString());
        }
        return rootObject.toJSONString() + "\n";
    }
}
