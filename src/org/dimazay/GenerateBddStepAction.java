package org.dimazay;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

public class GenerateBddStepAction extends AnAction {

    private StepGeneratorService stepGeneratorService = new StepGeneratorService();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiElement element = PsiActionUtils.getPsiClassFromContext(e, editor);
        stepGeneratorService.invokeStepGenerator(project, editor, element);
    }

    @Override
    public void update(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiElement element = PsiActionUtils.getPsiClassFromContext(e, editor);
        e.getPresentation().setVisible(stepGeneratorService.isStepGeneratorApplicable(project, editor, element));
    }
}
