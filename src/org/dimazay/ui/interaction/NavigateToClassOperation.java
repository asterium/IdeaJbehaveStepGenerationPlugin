package org.dimazay.ui.interaction;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.psi.PsiClass;

public class NavigateToClassOperation implements Runnable {

    private int lineNumber;

    private PsiClass psiClass;

    public NavigateToClassOperation(PsiClass psiClass) {
        this.psiClass = psiClass;
    }

    public NavigateToClassOperation(int lineNumber, PsiClass psiClass) {
        this.lineNumber = lineNumber;
        this.psiClass = psiClass;
    }

    @Override
    public void run() {
        new OpenFileDescriptor(psiClass.getProject(), psiClass.getContainingFile().getVirtualFile(), lineNumber, 0).navigate(true);
    }
}
