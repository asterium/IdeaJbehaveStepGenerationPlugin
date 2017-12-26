package org.dimazay.stepParser.models;

public class StepParameter {
    private static final String STEP_PARAMETER_STEP_TEMPLATE = "@Named(\"%s\") %s %s";

    private String parameterType;
    private String parameterName;

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterForMethodSignature() {
        return String.format(STEP_PARAMETER_STEP_TEMPLATE, parameterName, parameterType, parameterName);
    }
}
