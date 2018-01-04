package org.dimazay.stepParser.parameterExtraction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class ParameterPatterns {

    private ParameterPatterns(){}

    private static final List<String> STRING_PATTERNS = ImmutableList.of(
            "(?<=)\\$([a-zA-Z0-9]*)(?=\\s|$|,)(?<![integer|double]\\d)", //any word that is preceded by $ sign, but not already processed values (integer, double)
            "(?<=<)([A-Za-z]*)(?=>)" //any word inside < > - as for parametrized scenarios
    );

    private static final List<String> DOUBLE_PATTERNS = ImmutableList.of(
            "(?<=\\s)(-?\\d+[,\\.]\\d+)(?=\\s|$|,)" //any number preceded by whitespace and followes by whitespace, end of line, comma that has dot or comma as decimal separator
    );

    private static final List<String> INTEGER_PATTERNS = ImmutableList.of(
            "(?<=\\s|#|№)(?<!double|integer|string)(-?\\d+)(?=\\s|$|,)" //any number preceded by whitespace, # or № sign and followes by whitespace, end of line, comma
    );

    private static final List<String> BOOLEAN_PATTERNS = ImmutableList.of(
            "(?<=\\s)(true|false)(?=\\s|$|,)" //any number preceded by whitespace, # or № sign and followes by whitespace, end of line, comma
    );

    public static final Map<ParameterType, List<String>> PATTERNS = ImmutableMap.of(
            ParameterType.FLOAT, DOUBLE_PATTERNS,
            ParameterType.INTEGER, INTEGER_PATTERNS,
            ParameterType.STRING, STRING_PATTERNS,
            ParameterType.BOOLEAN, BOOLEAN_PATTERNS
    );
}
