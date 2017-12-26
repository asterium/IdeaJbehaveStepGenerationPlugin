package stepParser;

import java.util.List;

public class StepDefinitionModel {

    private static final String STEP_TEMPLATE = "%s(\"%s\")\npublic void %s%s{\n\n}";

    private StepType stepType;
    private String stepDescription;
    private String methodName;
    private List<StepParameterModel> stepParameters;

    public String getMethodText(){
        return String.format(STEP_TEMPLATE, stepType.getAnnotationText(), stepDescription, methodName, stepParameters);
    }
}
