package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.SavePromptDialog;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public abstract class AbstractWorker extends SwingWorker<Boolean, Void> {

    protected final Brain brain;
    private final boolean checkChangedFlag;

    public AbstractWorker(Brain brain, boolean checkChangedFlag) {
        this.brain = brain;
        this.checkChangedFlag = checkChangedFlag;
    }

    protected void done() {

        boolean status;
        try {
            // Retrieve the return value of backgroundTask.
            status = get();
            if (status) {
                update();
            }
        } catch (InterruptedException e) {
            // This is thrown if the thread's interrupted.
            e.printStackTrace();
        } catch (ExecutionException e) {
            // This is thrown if we throw an exception
            // from backgroundTask.
            e.printStackTrace();
        }
    }

    protected abstract void update();

    protected Boolean doInBackground() throws Exception {
        if (checkChangedFlag && brain.getChangedFlag()) {
            System.out.println("Something changed");
            SavePromptDialog dialog = new SavePromptDialog();
            dialog.setVisible(true);
            System.out.println("Entering checkDialog");
            checkDialog(dialog);
        }
        return backgroundTask();
    }

    private void checkDialog(SavePromptDialog dialog) throws InterruptedException {
        while (!dialog.hasFeedback()) {
            System.out.println("wait");
            Thread.sleep(1000);
        }
        int promptResult = dialog.getPromptResult();
        dialog.dispose();
        System.out.println(promptResult);
    }

    protected abstract Boolean backgroundTask() throws Exception;
}
