package com.hazebyte.crate.cratereloaded.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassUtilTest {

    @Test
    public void isInstanceOf_returnsTrue_forSameClass() {
        Class targetClazz = String.class;
        String sourceItem = "helloWorld";

        boolean result = ClassUtil.isInstanceOf(sourceItem, targetClazz);

        Assertions.assertTrue(result);
    }

    @Test
    public void isInstanceOf_returnsTrue_forListOfSameClass() {
        Class targetClazz = String.class;
        List<String> sourceItem = Arrays.asList("helloWorld");

        boolean result = ClassUtil.isInstanceOf(sourceItem, targetClazz);

        Assertions.assertTrue(result);
    }

    @Test
    public void isInstanceOf_returnsTrue_forSetOfSameClass() {
        Class targetClazz = String.class;
        Set<String> sourceItem = new HashSet<>(Arrays.asList("helloWorld"));

        boolean result = ClassUtil.isInstanceOf(sourceItem, targetClazz);

        Assertions.assertTrue(result);
    }

    @Test
    public void isInstanceOf_returnsFalse_forDifferentClass() {
        Class targetClazz = List.class;
        String sourceItem = "helloWorld";

        boolean result = ClassUtil.isInstanceOf(sourceItem, targetClazz);

        Assertions.assertFalse(result);
    }

    @Test
    public void isInstanceOf_returnsFalse_forEmptyList() {
        Class targetClazz = String.class;
        List<String> sourceItem = Arrays.asList();

        boolean result = ClassUtil.isInstanceOf(sourceItem, targetClazz);

        Assertions.assertFalse(result);
    }

    @Test
    public void isInstanceOf_returnsFalse_forSetOfDiffTarget() {
        Class targetClazz = String.class;
        List<Boolean> sourceItem = Arrays.asList(new Boolean(true));

        boolean result = ClassUtil.isInstanceOf(sourceItem, targetClazz);

        Assertions.assertFalse(result);
    }
}
