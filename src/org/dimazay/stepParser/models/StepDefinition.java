package org.dimazay.stepParser.models;

import org.dimazay.stepParser.parameterExtraction.ParameterType;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StepDefinition {

    private static final String STEP_TEMPLATE = "%s(\"%s\")\npublic void %s(%s){\n\n}";

    private StepType stepType;
    private String stepDescription;
    private String methodName;
    private List<StepParameter> stepParameters;

    public String getGeneratedMethod() {
        String annotationText = stepType.getAnnotationText();
        String stepParametersText = getMethodParametersString();
        String fullMethodName = getFullMethodName();
        return String.format(STEP_TEMPLATE, annotationText, stepDescription, fullMethodName, stepParametersText);
    }

    private String getFullMethodName(){
        return stepType.getDescriptionText().concat(methodName);
    }

    private String getMethodParametersString() {
        String stepParametersText = "";
        if (stepParameters != null && !stepParameters.isEmpty()) {
            List<StepParameter> sortedParameters = sortStepParametersByAppearanceOrder();
            stepParametersText = sortedParameters.stream()
                    .map(parameter -> parameter.getParameterForMethodSignature())
                    .collect(Collectors.joining(", "));
        }
        return stepParametersText;
    }

    private List<StepParameter> sortStepParametersByAppearanceOrder() {
        Comparator<StepParameter> parameterComparator = (first, second) -> {
            if(first.getParameterType() == ParameterType.EXAMPLES_TABLE) return 1;
            if(second.getParameterType() == ParameterType.EXAMPLES_TABLE) return -1;

            Integer firstIndex = stepDescription.indexOf(first.getParameterName());
            Integer secondIndex = stepDescription.indexOf(second.getParameterName());
            return firstIndex.compareTo(secondIndex);
        };

        return stepParameters
                .stream()
                .sorted(parameterComparator)
                .collect(Collectors.toList());
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
