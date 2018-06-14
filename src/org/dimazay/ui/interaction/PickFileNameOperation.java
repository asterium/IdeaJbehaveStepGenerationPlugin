package org.dimazay.ui.interaction;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiClass;
import org.dimazay.data.ClassInsertionData;
import org.dimazay.ui.interaction.filepicker.dialog.DecoratedTreeClassChooserFactory;
import org.dimazay.ui.interaction.filepicker.dialog.DecoratedTreeJavaClassChooserDialog;

public class PickFileNameOperation implements Computable<ClassInsertionData> {

    private Project project;

    public PickFileNameOperation(Project project) {
        this.project = project;
    }

    @Override
    public ClassInsertionData compute() {
        TreeClassChooserFactory classChooserFactory = DecoratedTreeClassChooserFactory.getInstance(project);
        TreeClassChooser projectScopeChooser = classChooserFactory.createProjectScopeChooser("Select class to generate step into");
        projectScopeChooser.showDialog();
        PsiClass selected = projectScopeChooser.getSelected();
        boolean isNavigationRequired = ((DecoratedTreeJavaClassChooserDialog) projectScopeChooser).shouldNavigateAfterDialogClose();

        return new ClassInsertionData(isNavigationRequired, selected);
    }
}
