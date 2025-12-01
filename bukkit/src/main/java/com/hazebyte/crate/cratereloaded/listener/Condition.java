package com.hazebyte.crate.cratereloaded.listener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
    String value();
}
