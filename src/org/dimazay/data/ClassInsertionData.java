package org.dimazay.data;

import com.intellij.psi.PsiClass;

public class ClassInsertionData {
    private boolean shouldGoToAfterInsert;
    private PsiClass psiClass;

    public ClassInsertionData(boolean shouldGoToAfterInsert, PsiClass psiClass) {
        this.shouldGoToAfterInsert = shouldGoToAfterInsert;
        this.psiClass = psiClass;
    }

    public boolean shouldGoToAfterInsert() {
        return shouldGoToAfterInsert;
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }
}
