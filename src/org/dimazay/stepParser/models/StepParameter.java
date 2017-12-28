package org.dimazay.stepParser.models;

import org.dimazay.stepParser.parameterExtraction.ParameterType;

public class StepParameter {
    private static final String NAMED_TEMPLATE = "@Named(\"%s\") ";
    private static final String PARAMETER_TEMPLATE =  "%s %s";

    private ParameterType parameterType;
    private String parameterName;
    private boolean shouldBeNamed;

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public void setIsNamed(boolean shouldBeNamed) {
        this.shouldBeNamed = shouldBeNamed;
    }

    public String getParameterName() {
        return parameterName;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public String getParameterForMethodSignature() {
        String namedAnnotation = shouldBeNamed ? String.format(NAMED_TEMPLATE, parameterName): "";
        return namedAnnotation + String.format(PARAMETER_TEMPLATE, parameterType.getTypeValue(), parameterName);
    }
}
