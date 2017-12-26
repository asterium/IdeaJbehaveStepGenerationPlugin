package org.dimazay.stepParser.models;

import java.util.List;
import java.util.stream.Collectors;

public class StepDefinition {

    private static final String STEP_TEMPLATE = "%s(\"%s\")\npublic void %s(%s){\n\n}";

    private StepType stepType;
    private String stepDescription;
    private String methodName;
    private List<StepParameter> stepParameters;

    public String getGeneratedMethod(){
        String annotationText = stepType.getAnnotationText();
        String stepParametersText = "";
        if(stepParameters != null && !stepParameters.isEmpty()){
            stepParametersText = stepParameters.stream()
                    .map(parameter -> parameter.getParameterForMethodSignature())
                    .collect(Collectors.joining(", "));
        }
        return String.format(STEP_TEMPLATE, annotationText, stepDescription, methodName, stepParametersText);
    }

    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setStepParameters(List<StepParameter> stepParameters) {
        this.stepParameters = stepParameters;
    }
}
