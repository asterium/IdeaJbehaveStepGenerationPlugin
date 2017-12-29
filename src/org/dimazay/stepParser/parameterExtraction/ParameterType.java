package org.dimazay.stepParser.parameterExtraction;

public enum ParameterType {
    INTEGER("integer", "int"),
    FLOAT("double", "double"),
    STRING("string", "String"),
    EXAMPLES_TABLE("examplesTable", "ExamplesTable");

    private final String parameterTypeText;
    private final String parameterTypeValue;

    ParameterType(String parameterTypeText, String parameterTypeValue) {
        this.parameterTypeText = parameterTypeText;
        this.parameterTypeValue = parameterTypeValue;
    }

    public String getTypeDescription() {
        return parameterTypeText;
    }

    public String getTypeValue() {
        return parameterTypeValue;
    }
}
