package org.dimazay.stepParser.models;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum StepType {
    GIVEN("given", "@Given"),
    WHEN("when", "@When"),
    THEN("then", "@Then"),
    AND("and", "");

    private final String typeString;
    private final String annotationDescription;

    public static StepType parse(String value) {
        return StepType.valueOf(value.trim().toUpperCase());
    }

    StepType(String typeString, String annotationDescription) {
        this.typeString = typeString;
        this.annotationDescription = annotationDescription;
    }

    public String getAnnotationText() {
        return annotationDescription;
    }

    public String getDescriptionText() {
        return typeString;
    }

    public static String buildRegexPatternToMatchStepTypes() {
        StepType[] possibleValues = StepType.class.getEnumConstants();
        return Arrays.stream(possibleValues)
                .map(enumInstance -> enumInstance.getDescriptionText())
                .collect(Collectors.joining("|"));
    }

}
