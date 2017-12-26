package org.dimazay;

import com.google.common.collect.ImmutableList;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;

import java.util.List;
import java.util.function.Predicate;

class DocumentUtils {

    private final Document document;

    private final List<String> allowedVerbs = ImmutableList.of("given", "when", "then", "and");
    private final Predicate<String> isChainedStepPredicate = string -> string.toLowerCase().trim().startsWith("and");

    public DocumentUtils(Document document) {
        this.document = document;
    }

    boolean isLineStartingWithAllowedVerbs(String line){
        String[] lineParts = line.split("\\s");
        if(lineParts.length <2) return false;
        String firstWord = lineParts[0];
        return allowedVerbs.contains(firstWord.toLowerCase());
    }

    String getActiveLineContents(Editor editor) {
        int lineNumber = getActiveLineNumber(editor);
        return getStringContentsByLineNumber(lineNumber);
    }

    private int getActiveLineNumber(Editor editor){
        int caretOffset = editor.getCaretModel().getOffset();
        return document.getLineNumber(caretOffset);
    }


    private String getStringContentsByLineNumber(int lineNumber) {
        int lineStartOffset = document.getLineStartOffset(lineNumber);
        int lineEndOffset = document.getLineEndOffset(lineNumber);

        return document.getText(TextRange.create(lineStartOffset,lineEndOffset));
    }

    String findLinkedStepDefinitionIfAny(Editor editor){
        int activeLineIndex = getActiveLineNumber(editor);
        String activeLineContents = getActiveLineContents(editor);
        String linkedChainLineContents = null;

        boolean isChainedStep = isChainedStepPredicate.test(activeLineContents);
        while (isChainedStep && activeLineIndex >0){
            activeLineIndex--;
            linkedChainLineContents = getStringContentsByLineNumber(activeLineIndex);
            isChainedStep = isChainedStepPredicate.test(linkedChainLineContents);
        }
        return linkedChainLineContents;
    }
}