package org.dimazay.stepParser;

public class StringUtils {
    private StringUtils(){}

    public static String buildRegexIgnoringFirstCapital(String part){
        char firstSymbol = part.charAt(0);
        char firstSymbolUpperCase = Character.toUpperCase(firstSymbol);
        char firstSymbolLowerCase = Character.toLowerCase(firstSymbol);

        String rest = part.substring(1);

        return String.format("[%s%s]"+rest,firstSymbolUpperCase, firstSymbolLowerCase);
    }

}
