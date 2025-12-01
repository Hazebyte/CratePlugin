package com.hazebyte.crate.utils;

public class WordUtils {

    /** Capitalize will capitalize the first letter after each space. */
    public static String capitalize(String str) {
        return capitalize(str, null);
    }

    public static String capitalize(String str, char[] delimiters) {
        int delimLen = delimiters == null ? -1 : delimiters.length;
        if (str != null && str.length() != 0 && delimLen != 0) {
            int strLen = str.length();
            StringBuilder buffer = new StringBuilder(strLen);
            boolean capitalizeNext = true;

            for (int i = 0; i < strLen; ++i) {
                char ch = str.charAt(i);
                if (isDelimiter(ch, delimiters)) {
                    buffer.append(ch);
                    capitalizeNext = true;
                } else if (capitalizeNext) {
                    buffer.append(Character.toTitleCase(ch));
                    capitalizeNext = false;
                } else {
                    buffer.append(ch);
                }
            }

            return buffer.toString();
        } else {
            return str;
        }
    }

    /**
     * CapitalizeFully will turn lowercase the entire string and capitalize the letter after each
     * space.
     */
    public static String capitalizeFully(String str) {
        return capitalizeFully(str, null);
    }

    public static String capitalizeFully(String str, char[] delimiters) {
        int delimLen = delimiters == null ? -1 : delimiters.length;
        if (str != null && str.length() != 0 && delimLen != 0) {
            str = str.toLowerCase();
            return capitalize(str, delimiters);
        } else {
            return str;
        }
    }

    private static boolean isDelimiter(char ch, char[] delimiters) {
        if (delimiters == null) {
            return Character.isWhitespace(ch);
        } else {
            for (char delimiter : delimiters) {
                if (ch == delimiter) {
                    return true;
                }
            }

            return false;
        }
    }
}
