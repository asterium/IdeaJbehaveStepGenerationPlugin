package org.dimazay;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import org.dimazay.GenerateBddStepIntention;

public class GenerateBddStepAction extends AnAction {

    private StepGeneratorService stepGeneratorService = new StepGeneratorService();

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiElement element = getPsiClassFromContext(e, editor);
        stepGeneratorService.invokeStepGenerator(project,editor,element);
    }

    @Override
    public void update(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        PsiElement element = getPsiClassFromContext(e, editor);
        e.getPresentation().setVisible(stepGeneratorService.isStepGeneratorApplicable(project,editor,element));
    }

    private PsiElement getPsiClassFromContext(AnActionEvent e, Editor editor) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        return elementAt;
    }
}
