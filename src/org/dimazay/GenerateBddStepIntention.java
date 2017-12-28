package org.dimazay;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPlainText;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.dimazay.stepParser.StepDefinitionProcessor;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GenerateBddStepIntention extends PsiElementBaseIntentionAction implements IntentionAction {

    private final StepGeneratorService stepGeneratorService = new StepGeneratorService();

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        return stepGeneratorService.isStepGeneratorApplicable(project,editor,psiElement);
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
       stepGeneratorService.invokeStepGenerator(project,editor,psiElement);
    }



    @Override
    public boolean startInWriteAction() {
        return false;
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "Generates template of jbehave step definition based on step description";
    }

    @Nls
    @NotNull
    @Override
    public String getText() {
        return "Generate BDD step...";
    }
}
