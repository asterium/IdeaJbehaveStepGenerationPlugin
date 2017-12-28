package org.dimazay.stepParser.parameterExtraction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class ParameterPatterns {

    private ParameterPatterns(){}

    private static final List<String> STRING_PATTERNS = ImmutableList.of(
            "(?<=)\\$([a-zA-Z0-9]*)(?=\\s|$)(?<![integer|double|string]\\d)",
            "(?<=<)([A-Za-z]*)(?=>)"
    );

    private static final List<String> DOUBLE_PATTERNS = ImmutableList.of(
            "(?<=\\s)(-?\\d+[,\\.]\\d+)(?=\\s|$)"
    );

    private static final List<String> INTEGER_PATTERNS = ImmutableList.of(
            "(?<=\\s|#|№)(?<!double|integer|string)(-?\\d+)(?=\\s|$)"
    );

    public static final Map<ParameterType, List<String>> PATTERNS = ImmutableMap.of(
            ParameterType.FLOAT, DOUBLE_PATTERNS,
            ParameterType.INTEGER, INTEGER_PATTERNS,
            ParameterType.STRING, STRING_PATTERNS
    );
}
