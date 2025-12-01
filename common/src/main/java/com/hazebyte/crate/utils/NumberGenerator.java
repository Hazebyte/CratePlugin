package com.hazebyte.crate.utils;

import java.util.concurrent.ThreadLocalRandom;

public class NumberGenerator {

    private NumberGenerator() {}

    /** Returns a number between the min (inclusive) and max (inclusive). */
    public static int range(int min, int max) {
        if (min >= max) return min;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
