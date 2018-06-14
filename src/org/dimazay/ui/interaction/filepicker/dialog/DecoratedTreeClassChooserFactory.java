package org.dimazay.ui.interaction.filepicker.dialog;

import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.ide.util.TreeClassChooserFactoryImpl;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class DecoratedTreeClassChooserFactory extends TreeClassChooserFactoryImpl {
    private final Project myProject;

    public DecoratedTreeClassChooserFactory(@NotNull Project project) {
        super(project);
        myProject = project;
    }

    public static TreeClassChooserFactory getInstance(Project project) {
        return (DecoratedTreeClassChooserFactory) ServiceManager.getService(project, DecoratedTreeChooser.class);
    }

    @NotNull
    @Override
    public TreeClassChooser createProjectScopeChooser(String title) {
        return new DecoratedTreeJavaClassChooserDialog(title, this.myProject);
    }
}
