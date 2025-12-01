package com.hazebyte.crate.cratereloaded.locale;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageKey {
    private static final AtomicInteger counter = new AtomicInteger(1);
    private static final Map<String, MessageKey> keyMap = new ConcurrentHashMap<>();
    private static final int id = counter.incrementAndGet();
    private final String key;

    public MessageKey(String key) {
        this.key = key;
    }

    public static MessageKey of(String messageKey) {
        return keyMap.computeIfAbsent(messageKey.toLowerCase().intern(), MessageKey::new);
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(MessageKey object) {
        return (this == object);
    }

    public String getKey() {
        return key;
    }

    public MessageKey getMessageKey() {
        return this;
    }
}
