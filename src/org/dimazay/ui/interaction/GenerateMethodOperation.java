package org.dimazay.ui.interaction;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.dimazay.StepGeneratorService;
import org.dimazay.data.ClassInsertionData;
import org.dimazay.data.InvocationPointData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;

public class GenerateMethodOperation implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateMethodOperation.class);
    private final StepGeneratorService stepGeneratorService;
    private final InvocationPointData invocationPointData;
    private final RunnableFuture<ClassInsertionData> willBeClassInsertionData;

    public GenerateMethodOperation(InvocationPointData invocationPointData, RunnableFuture<ClassInsertionData> willBeClassInsertionData) {
        this.invocationPointData = invocationPointData;
        this.stepGeneratorService = new StepGeneratorService();
        this.willBeClassInsertionData = willBeClassInsertionData;
    }

    @Override
    public void run() {
        PsiClass classToUpdate = getClassInsertionData().getPsiClass();
        JavaPsiFacade psiFacade = JavaPsiFacade.getInstance(invocationPointData.getProject());
        PsiMethod newlyCreatedMethod = psiFacade.getElementFactory().createMethodFromText(generateMethodText(), classToUpdate);

        Integer linePosition = WriteCommandAction.runWriteCommandAction(invocationPointData.getProject(), new InsertMethodIntoClassOperation(classToUpdate, newlyCreatedMethod));
        if (getClassInsertionData().shouldGoToAfterInsert()) {
            ApplicationManager.getApplication().invokeLater(new NavigateToClassOperation(linePosition, classToUpdate));
        }
    }

    private ClassInsertionData getClassInsertionData() {
        ClassInsertionData result = null;
        try {
            result = willBeClassInsertionData.get();
        } catch (InterruptedException e) {
            LOGGER.error("Failed to wait until result of future fo ClassInsertionData instance", e);
        } catch (ExecutionException e) {
            LOGGER.error("Failed to get value from future ClassInsertionData instance", e);
        }
        return result;
    }

    private String generateMethodText() {
        return stepGeneratorService.getGeneratedMethodText(invocationPointData.getProject(), invocationPointData.getEditor(), invocationPointData.getPsiElement());
    }
}
