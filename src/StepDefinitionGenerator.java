import com.google.common.collect.ImmutableList;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StepDefinitionGenerator extends PsiElementBaseIntentionAction implements IntentionAction{

    List<String> allowedVerbs = ImmutableList.of("given", "when", "then", "and");

    @Nullable
    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (psiFile == null || editor == null) {
            return null;
        }
        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAt = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
        return psiClass;
    }



    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        if(psiElement == null) return false;
        if(!psiElement.isWritable()) return false;
        if(psiElement instanceof PsiPlainText){
            return isLineStartingWithAllowedVerbs(extractActiveLineContents(project,editor,psiElement));
        }
        return false;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        extractActiveLineContents(project, editor, psiElement);
    }

    private boolean isLineStartingWithAllowedVerbs(String line){
        String[] lineParts = line.split("\\s");
        if(lineParts.length <2) return false;
        String firstWord = lineParts[0];
        return allowedVerbs.contains(firstWord.toLowerCase());
    }

    private String extractActiveLineContents(@NotNull Project project, Editor editor, PsiElement psiElement) {
        int caretOffset = editor.getCaretModel().getOffset();
        Document document = PsiDocumentManager.getInstance(project).getDocument(psiElement.getContainingFile());

        int lineNumber = document.getLineNumber(caretOffset);
        int lineStartOffset = document.getLineStartOffset(lineNumber);
        int lineEndOffset = document.getLineEndOffset(lineNumber);

        return document.getText(TextRange.create(lineStartOffset,lineEndOffset));
    }

    @Override
    public boolean startInWriteAction() {
        return false;
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
        return "Generate BDD step...";
    }
}
