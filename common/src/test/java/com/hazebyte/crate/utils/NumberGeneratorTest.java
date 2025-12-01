package com.hazebyte.crate.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumberGeneratorTest {

    @Test
    public void generatorReturnsCorrectBound() {
        int min = 0;
        int max = 5;
        int num = NumberGenerator.range(min, max);
        Assertions.assertTrue(num >= min && num <= max);
    }

    @Test
    public void generatorReturnsBoundForLargeMin() {
        int min = 10;
        int max = 15;
        int num = NumberGenerator.range(min, max);
        Assertions.assertTrue(num >= min && num <= max);
    }

    @Test
    public void returnExactValueForMinGreaterThanMax() {
        int min = 10;
        int max = 9;
        int num = NumberGenerator.range(min, max);
        Assertions.assertTrue(num == min);
    }

    @Test
    public void generatorReturnsCorrectBoundLoadTest() {
        for (int i = 0; i < 10000; i++) {
            int min = 1;
            int max = 5;
            int num = NumberGenerator.range(min, max);
            Assertions.assertTrue(num >= min && num <= max);
        }
    }
}
