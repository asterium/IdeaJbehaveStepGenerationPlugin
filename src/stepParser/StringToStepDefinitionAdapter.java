package stepParser;

public class StringToStepDefinitionAdapter {
    private String stepDescription;
    private StepDefinitionModel stepDefinitionModel;

    public StringToStepDefinitionAdapter(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    public StepDefinitionModel getStepDefinitionModel() {
        return stepDefinitionModel;
    }

    private StepType extractStepType(String stepDescription) {
        String[] wordsOfDescription = stepDescription.split("\\s");
        if (wordsOfDescription.length < 2) {
            return null;
        }

        //TODO: will fail if first word is AND. RETHINK error handling
        StepType stepType = StepType.parse(wordsOfDescription[0]);
        return stepType;
    }


}
