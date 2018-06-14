package org.dimazay.ui.interaction;

import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

public class InsertMethodIntoClassOperation implements Computable<Integer> {

    private PsiClass classToInsert;
    private PsiMethod methodToInsert;

    public InsertMethodIntoClassOperation(PsiClass classToInsert, PsiMethod methodToInsert) {
        this.classToInsert = classToInsert;
        this.methodToInsert = methodToInsert;
    }

    @Override
    public Integer compute() {
        PsiElement element = classToInsert.addBefore(methodToInsert, classToInsert.getLastChild());
        int startOffsetInParent = element.getTextOffset();
        return StringUtil.offsetToLineNumber(element.getContainingFile().getText(), startOffsetInParent);
    }
}
