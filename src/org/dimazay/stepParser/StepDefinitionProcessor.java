package org.dimazay.stepParser;

import org.dimazay.stepParser.models.StepDefinition;
import org.dimazay.stepParser.models.StepType;

public class StepDefinitionProcessor {

    public String generateStepDefinition(String stepDescription, String nextLine, String linkedLine) {
        boolean isNextLineTable = isLineExamplesTable(nextLine);
        StepDefinition stepDefinition = new StringToStepDefinitionAdapter(stepDescription, isNextLineTable).getStepDefinition();
        if (linkedLine != null) {
            StepType anchorStepType = new StringToStepDefinitionAdapter(linkedLine, false).extractStepType();
            stepDefinition.setStepType(anchorStepType);
        }

        return stepDefinition.getGeneratedMethod();
    }

    private boolean isLineExamplesTable(String line) {
        return line.trim().startsWith("|");
    }

    //TODO: will stay here until end of code review
//    public static void main(String[] args) {
//        String test6 = new StepDefinitionProcessor().generateStepDefinition("Then I want to see <someValue> as a result in column #1 divided by 34.32");
//        System.out.println(test6);
//
//        String test1 = new StepDefinitionProcessor().generateStepDefinition("Given I am user and i am on the home screen");
//        System.out.println(test1);
//
//        String test2 = new StepDefinitionProcessor().generateStepDefinition("When I select 2 as first operand");
//        System.out.println(test2);
//
//        String test3 = new StepDefinitionProcessor().generateStepDefinition("When I select -3.123 as second operand");
//        System.out.println(test3);
//
//        String test4 = new StepDefinitionProcessor().generateStepDefinition("Then I want to see <someValue> as a result");
//        System.out.println(test4);
//
//        String test5 = new StepDefinitionProcessor().generateStepDefinition("Then I want to see <someValue> as a result in column #1");
//        System.out.println(test5);
//
//        String test7 = new StepDefinitionProcessor().generateStepDefinition("Then I want to see <someValue> #1 23 45.6 7 89 1 33COWS");
//        System.out.println(test7);
//
//        String test8 = new StepDefinitionProcessor().generateStepDefinition("Then metadata is present with fields $title $password $value");
//        System.out.println(test8);
//    }
}
