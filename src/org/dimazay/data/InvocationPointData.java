package org.dimazay.data;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

public class InvocationPointData {
    private Project project;
    private Editor editor;
    private PsiElement psiElement;

    public InvocationPointData(Project project, Editor editor, PsiElement psiElement) {
        this.project = project;
        this.editor = editor;
        this.psiElement = psiElement;
    }

    public Project getProject() {
        return project;
    }

    public Editor getEditor() {
        return editor;
    }

    public PsiElement getPsiElement() {
        return psiElement;
    }
}
