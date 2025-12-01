package com.hazebyte.crate.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WordUtilsTest {

    @Test
    public void capitalizeReturnsCorrectString() {
        String test = "hello WORLD";
        String expected = "Hello WORLD";

        String capitalized = WordUtils.capitalize(test);
        Assertions.assertEquals(expected, capitalized);
    }

    @Test
    public void capitalizeFullyReturnsCorrectString() {
        String test = "hello WORLD";
        String expected = "Hello World";

        String capitalized = WordUtils.capitalizeFully(test);
        Assertions.assertEquals(expected, capitalized);
    }
}
