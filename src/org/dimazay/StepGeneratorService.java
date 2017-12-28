package org.dimazay;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPlainText;
import org.dimazay.stepParser.StepDefinitionProcessor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class StepGeneratorService {
    private final StepDefinitionProcessor stepDefinitionProcessor = new StepDefinitionProcessor();

    public boolean isStepGeneratorApplicable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement){
        if (psiElement == null) return false;
        if (!psiElement.isWritable()) return false;
        if (psiElement instanceof PsiPlainText) {
            Document activeDocument = PsiDocumentManager.getInstance(project).getDocument(psiElement.getContainingFile());
            DocumentUtils documentUtils = new DocumentUtils(activeDocument);

            String activeLineContents = documentUtils.getActiveLineContents(editor);
            return documentUtils.isLineStartingWithAllowedVerbs(activeLineContents);
        }
        return false;
    }

    public void invokeStepGenerator(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement){
        Document activeDocument = PsiDocumentManager.getInstance(project).getDocument(psiElement.getContainingFile());
        DocumentUtils documentUtils = new DocumentUtils(activeDocument);
        String activeLineContents = documentUtils.getActiveLineContents(editor);
        String linkedChainLineContents = documentUtils.findLinkedStepDefinitionIfAny(editor);


        String methodText = linkedChainLineContents == null ?
                stepDefinitionProcessor.generateStepDefinition(activeLineContents) :
                stepDefinitionProcessor.generateStepDefinition(activeLineContents, linkedChainLineContents);
        pasteTextToClipboard(methodText);
    }

    private void pasteTextToClipboard(String methodText) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection stringToInsertToClipBoard = new StringSelection(methodText);
        clipboard.setContents(stringToInsertToClipBoard, null);
    }
}
