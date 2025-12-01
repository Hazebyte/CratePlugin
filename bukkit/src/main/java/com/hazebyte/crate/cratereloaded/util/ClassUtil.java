package com.hazebyte.crate.cratereloaded.util;

import java.util.Collection;
import java.util.Optional;

public class ClassUtil {

    public static boolean isInstanceOf(Object source, Class target) {
        if (target.isInstance(source)) {
            return true;
        }

        if (!(source instanceof Collection)) {
            return false;
        }

        Collection collection = (Collection) source;
        if (collection.size() <= 0) {
            return false;
        }

        Optional optionalSource = collection.stream().findFirst();
        if (!optionalSource.isPresent()) {
            return false;
        }

        return target.isInstance(optionalSource.get());
    }
}
