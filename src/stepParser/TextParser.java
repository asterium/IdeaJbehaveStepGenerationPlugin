package stepParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TextParser {
    String extractStepParameters(String stepDescription) {
        return null;
    }

    String extractStepMethodName(String stepDescription) {
        return null;
    }

    String extractParsedStepDescription(String stepDescription) {
        String regex = "([Gg]iven|[Ww]hen|[Th]en|[Aa]nd)(.*)";
        Pattern stepPattern = Pattern.compile(regex);
        Matcher matcher = stepPattern.matcher(stepDescription);
        if(!matcher.matches()){
            return new String();
        }
        return matcher.group(2).trim();
    }

    StepType extractAnnotationType(String stepDescription) {
        String[] wordsOfDescription = stepDescription.split("\\s");
        if (wordsOfDescription.length < 2) {
            return null;
        }

        //TODO: will fail if first word is AND. RETHINK error handling
        StepType stepType = StepType.parse(wordsOfDescription[0]);
        return stepType;
    }
}
