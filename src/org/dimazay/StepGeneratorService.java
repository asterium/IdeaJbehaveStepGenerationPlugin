package org.dimazay;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import org.dimazay.stepParser.StepDefinitionProcessor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class StepGeneratorService {
    private final StepDefinitionProcessor stepDefinitionProcessor = new StepDefinitionProcessor();

    public boolean isStepGeneratorApplicable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        if (psiElement == null) return false;
        if (!psiElement.isWritable()) return false;
        Document activeDocument = PsiDocumentManager.getInstance(project).getDocument(psiElement.getContainingFile());
        DocumentUtils documentUtils = new DocumentUtils(activeDocument);

        String activeLineContents = documentUtils.getActiveLineContents(editor);
        return documentUtils.isLineStartingWithAllowedVerbs(activeLineContents);
    }

    public void invokeStepGenerator(Project project, Editor editor, PsiElement psiElement) {
        String methodText = getGeneratedMethodText(project, editor, psiElement);
        pasteTextToClipboard(methodText);
    }

    public String getGeneratedMethodText(Project project, Editor editor, PsiElement psiElement) {
        Document activeDocument = PsiDocumentManager.getInstance(project).getDocument(psiElement.getContainingFile());
        DocumentUtils documentUtils = new DocumentUtils(activeDocument);

        String activeLineContents = documentUtils.getActiveLineContents(editor);
        String linkedChainLineContents = documentUtils.findLinkedStepDefinitionIfAny(editor);
        String nextLineContents = documentUtils.getNextLineContents(editor);

        return stepDefinitionProcessor.generateStepDefinition(activeLineContents, nextLineContents, linkedChainLineContents);
    }

    private void pasteTextToClipboard(String methodText) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection stringToInsertToClipBoard = new StringSelection(methodText);
        clipboard.setContents(stringToInsertToClipBoard, null);
    }
}
