package com.hazebyte.crate.cratereloaded.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class MoreObjects {

    public static <T> T firstNonNull(T... objects) {
        return objects != null && objects.length > 0 ? objects[0] : null;
    }

    public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return first != null ? first : (second);
    }

    public static <T> T firstNonNull(@Nullable List<T> first) {
        return first != null && first.size() > 0 ? first.get(0) : null;
    }

    public static boolean isNullOrEmpty(final Collection<?> c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNullOrEmpty(final Map<?, ?> m) {
        return m == null || m.isEmpty();
    }
}
