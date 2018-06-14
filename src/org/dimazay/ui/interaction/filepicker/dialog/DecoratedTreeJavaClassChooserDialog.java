package org.dimazay.ui.interaction.filepicker.dialog;

import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.TreeJavaClassChooserDialog;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DecoratedTreeJavaClassChooserDialog extends TreeJavaClassChooserDialog {
    private Action selectAndNavigate;
    private boolean shouldNavigateAfterDialogClose;

    public DecoratedTreeJavaClassChooserDialog(String title, Project project) {
        super(title, project);
    }

    public DecoratedTreeJavaClassChooserDialog(String title, Project project, @Nullable PsiClass initialClass) {
        super(title, project, initialClass);
    }

    public DecoratedTreeJavaClassChooserDialog(String title, @NotNull Project project, GlobalSearchScope scope, ClassFilter classFilter, @Nullable PsiClass initialClass) {
        super(title, project, scope, classFilter, initialClass);
    }

    public DecoratedTreeJavaClassChooserDialog(String title, @NotNull Project project, GlobalSearchScope scope, @Nullable ClassFilter classFilter, PsiClass baseClass, @Nullable PsiClass initialClass, boolean isShowMembers) {
        super(title, project, scope, classFilter, baseClass, initialClass, isShowMembers);
    }

    @NotNull
    @Override
    protected Action[] createActions() {
        setOKButtonText("Select");
        List<Action> existingActions = new ArrayList<>(Arrays.asList(super.createActions()));
        selectAndNavigate = new SelectAndNavigateAction("Select and navigate");
        existingActions.add(1, selectAndNavigate);
        return existingActions.stream().toArray(Action[]::new);
    }

    @Override
    public void setOKActionEnabled(boolean isEnabled) {
        super.setOKActionEnabled(isEnabled);
        if (selectAndNavigate != null) {
            selectAndNavigate.setEnabled(isEnabled);
        }
    }

    public boolean shouldNavigateAfterDialogClose() {
        return shouldNavigateAfterDialogClose;
    }

    private class SelectAndNavigateAction extends AbstractAction {

        public SelectAndNavigateAction(String name) {
            super(name);
            putValue("MnemonicKey", KeyEvent.VK_ENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            shouldNavigateAfterDialogClose = true;
            doOKAction();
        }
    }
}
