package com.hazebyte.crate.cratereloaded.util.format;

public class Digits {
    public static boolean containsDigit(String str) {
        boolean containsDigit = false;
        if (str != null && !str.isEmpty()) {
            for (char c : str.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }
        return containsDigit;
    }

    public static boolean isAllDigits(String str) {
        if (str != null && !str.isEmpty()) {
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        }
        return true;
    }
}
