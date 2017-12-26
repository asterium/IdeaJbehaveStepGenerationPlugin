package stepParser;

public class StepDefinitionGenerator {



    private TextParser textParser = new TextParser();

    private StringToStepDefinitionAdapter stepParser;

    public String generateStepDefinition(String stepDescription){
        stepParser = new StringToStepDefinitionAdapter(stepDescription);
        StepDefinitionModel stepDefinition = stepParser.getStepDefinitionModel();
        return stepDefinition.getMethodText();
    }

    public static void main(String[] args) {
       String test1 = new StepDefinitionGenerator().generateStepDefinition("Given I am user and i am on the home screen");
        System.out.println(test1);
    }

}
