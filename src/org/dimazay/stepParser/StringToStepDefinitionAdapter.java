package org.dimazay.stepParser;

import org.apache.commons.lang.WordUtils;
import org.dimazay.stepParser.models.StepDefinition;
import org.dimazay.stepParser.models.StepParameter;
import org.dimazay.stepParser.models.StepType;
import org.dimazay.stepParser.parameterExtraction.ParameterPatterns;
import org.dimazay.stepParser.parameterExtraction.ParameterType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringToStepDefinitionAdapter {
    private static final String REGEX_TEMPLATE = "(%s)\\s(.*)";

    private final String stepDescription;
    private final boolean hasTableParameter;
    private List<StepParameter> parameters;

    public StringToStepDefinitionAdapter(String stepDescription, boolean hasTableParameter) {
        this.stepDescription = stepDescription;
        this.hasTableParameter = hasTableParameter;
    }

    public StringToStepDefinitionAdapter(String stepDescription) {
        this(stepDescription, false);
    }

    public StepDefinition getStepDefinition() {
        StepDefinition stepDefinition = new StepDefinition();
        stepDefinition.setStepType(extractStepType());
        stepDefinition.setMethodName(extractMethodName());
        stepDefinition.setStepDescription(processStepDescriptionAndExtractParameters());
        stepDefinition.setStepParameters(parameters);
        return stepDefinition;
    }

    public StepType extractStepType() {
        String stepTypeStr = parseDescriptionAndGetGroupByIndex(1);
        return StepType.parse(stepTypeStr);
    }

    public String extractMethodName() {
        String parsedDescription = parseDescriptionAndGetGroupByIndex(2);
        String sanitizedDescription = parsedDescription.replaceAll("[^A-Za-z\\s]", "");
        String capitalizedStep = WordUtils.capitalizeFully(sanitizedDescription);

        return capitalizedStep.replaceAll("\\s", "");
    }

    private String processStepDescriptionAndExtractParameters() {
        parameters = new ArrayList<>();

        String parsedDescription = parseDescriptionAndGetGroupByIndex(2);
        String processedString = processAndExtractParametersByType(parsedDescription, ParameterType.FLOAT);
        processedString = processAndExtractParametersByType(processedString, ParameterType.INTEGER);
        processedString = processAndExtractParametersByType(processedString, ParameterType.BOOLEAN);
        processedString = processAndExtractParametersByType(processedString, ParameterType.STRING);
        processedString = sanitizeQuotationMarks(processedString);

        addExamplesTableParameterIfRequired();

        return processedString;
    }

    private void addExamplesTableParameterIfRequired() {
        if (hasTableParameter) {
            StepParameter exampleTableParameter = new StepParameter();
            exampleTableParameter.setParameterType(ParameterType.EXAMPLES_TABLE);
            exampleTableParameter.setParameterName(ParameterType.EXAMPLES_TABLE.getTypeDescription());
            parameters.add(exampleTableParameter);
        }
    }

    private String processAndExtractParametersByType(String stepDescription, ParameterType parameterType) {
        List<String> patterns = ParameterPatterns.PATTERNS.get(parameterType);
        String processedStepDescription = stepDescription;

        for (String pattern : patterns) {
            processedStepDescription = processAndExtractParametersByRegex(processedStepDescription, pattern, parameterType);
        }
        return processedStepDescription;
    }

    @NotNull
    private String processAndExtractParametersByRegex(String stepDescription, String regex, ParameterType parameterType) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(stepDescription);

        StringBuffer processedStringBuffer = new StringBuffer();

        int parameterCount = 1;
        while (matcher.find()) {
            boolean isPreprocessed = matcher.group().startsWith("$");

            StepParameter parameter = buildParameter(matcher.group(), parameterType, parameterCount);
            String patternReplacementValue = getPatternReplacementText(parameter, isPreprocessed);
            parameters.add(parameter);

            matcher.appendReplacement(processedStringBuffer, Matcher.quoteReplacement(patternReplacementValue));
            parameterCount++;
        }
        matcher.appendTail(processedStringBuffer);
        return processedStringBuffer.toString();
    }

    private String getPatternReplacementText(StepParameter parameter, boolean isPreprocessed) {
        boolean shouldPrefixBeApplied = (parameter.getParameterType() != ParameterType.STRING) || isPreprocessed;
        String prefix = shouldPrefixBeApplied ? "$" : "";
        return prefix + parameter.getParameterName();
    }

    private StepParameter buildParameter(String matchedText, ParameterType parameterType, int parameterCount) {
        String parameterName = parameterType == ParameterType.STRING ?
                matchedText.replaceAll("\\$", "") :
                parameterType.getTypeDescription() + parameterCount;

        StepParameter parameter = new StepParameter();
        parameter.setParameterName(parameterName);
        parameter.setParameterType(parameterType);
        parameter.setIsNamed(parameterType == ParameterType.STRING);

        return parameter;
    }

    @NotNull
    private String parseDescriptionAndGetGroupByIndex(int index) {
        String regex = String.format(REGEX_TEMPLATE, StepType.buildRegexPatternToMatchStepTypes());
        Pattern stepRegexPattern = Pattern.compile(regex);
        Matcher matcher = stepRegexPattern.matcher(stepDescription.trim());
        if (!matcher.matches()) {
            return "";
        }
        return matcher.group(index).trim();
    }

    private String sanitizeQuotationMarks(String stringToProcess) {
        return stringToProcess.replaceAll("\"", "\\\\\"");
    }
}
