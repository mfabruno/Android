package com.example.a6749.app2test.Utils;

/**
 * Created by 6749 on 02/11/2017.
 */

public class StringUtils {

    public static final String StringEmpty="";

    public static boolean IsNullEmptyOrWhiteSpace(final String s)
    {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }

}
