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
    private final List<String> gherkinStepTypes = ImmutableList.of("given", "when", "then");
    private final Predicate<String> hasConcreteStepType = string -> {
        String firstWord = string.split("\\s")[0];
        return gherkinStepTypes.contains(firstWord.toLowerCase());
    };

    public DocumentUtils(Document document) {
        this.document = document;
    }

    boolean isLineStartingWithAllowedVerbs(String line) {
        String[] lineParts = line.trim().split("\\s");
        if (lineParts.length < 2) {
            return false;
        }
        String firstWord = lineParts[0];
        return allowedVerbs.contains(firstWord.toLowerCase());
    }

    String getActiveLineContents(Editor editor) {
        int lineNumber = getActiveLineNumber(editor);
        return getStringContentsByLineNumber(lineNumber);
    }

    String getNextLineContents(Editor editor) {
        int lineNumber = getActiveLineNumber(editor);
        return getStringContentsByLineNumber(lineNumber+1);
    }

    private int getActiveLineNumber(Editor editor) {
        int caretOffset = editor.getCaretModel().getOffset();
        return document.getLineNumber(caretOffset);
    }

    private String getStringContentsByLineNumber(int lineNumber) {
        if(document.getLineCount() <= lineNumber){
            return "";
        }
        int lineStartOffset = document.getLineStartOffset(lineNumber);
        int lineEndOffset = document.getLineEndOffset(lineNumber);
        return document.getText(TextRange.create(lineStartOffset, lineEndOffset));
    }

    String findLinkedStepDefinitionIfAny(Editor editor) {
        int activeLineIndex = getActiveLineNumber(editor);
        String activeLineContents = getActiveLineContents(editor);
        String linkedChainLineContents = null;

        boolean hasConcreteStepType = this.hasConcreteStepType.test(activeLineContents);
        while (!hasConcreteStepType && activeLineIndex > 0) {
            activeLineIndex--;
            linkedChainLineContents = getStringContentsByLineNumber(activeLineIndex);
            hasConcreteStepType = this.hasConcreteStepType.test(linkedChainLineContents);
        }
        return linkedChainLineContents;
    }
}
