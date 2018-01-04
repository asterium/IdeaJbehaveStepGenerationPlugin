package org.dimazay.stepParser;

import org.dimazay.stepParser.models.StepDefinition;
import org.dimazay.stepParser.models.StepType;

import javax.annotation.Nullable;

public class StepDefinitionProcessor {

    public String generateStepDefinition(String stepDescription, String nextLine, @Nullable String linkedLine) {
        boolean isNextLineTable = isLineExamplesTable(nextLine);
        StepDefinition stepDefinition = new StringToStepDefinitionAdapter(stepDescription, isNextLineTable).getStepDefinition();
        if (linkedLine != null) {
            StepType anchorStepType = new StringToStepDefinitionAdapter(linkedLine).extractStepType();
            stepDefinition.setStepType(anchorStepType);
        }

        return stepDefinition.getGeneratedMethod();
    }

    private boolean isLineExamplesTable(String line) {
        return line.trim().startsWith("|");
    }
}
