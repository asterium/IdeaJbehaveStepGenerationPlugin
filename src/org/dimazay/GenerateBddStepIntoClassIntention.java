package org.dimazay;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.dimazay.data.ClassInsertionData;
import org.dimazay.data.InvocationPointData;
import org.dimazay.ui.interaction.GenerateMethodOperation;
import org.dimazay.ui.interaction.PickFileNameOperation;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class GenerateBddStepIntoClassIntention extends PsiElementBaseIntentionAction implements IntentionAction {
    private StepGeneratorService stepGeneratorService = new StepGeneratorService();

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        InvocationPointData invocationPointData = new InvocationPointData(project, editor, psiElement);
        RunnableFuture<ClassInsertionData> willGetFileNames = new FutureTask<>(() -> ApplicationManager.getApplication().runReadAction(new PickFileNameOperation(project)));
        ApplicationManager.getApplication().invokeLater(willGetFileNames);
        ApplicationManager.getApplication().invokeLater(new GenerateMethodOperation(invocationPointData, willGetFileNames));
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        return stepGeneratorService.isStepGeneratorApplicable(project, editor, psiElement);
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
        return "Generate BDD step into class...";
    }
}
