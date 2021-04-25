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
            SavePromptDialog dialog = new SavePromptDialog();
            dialog.setVisible(true);
        }
        return backgroundTask();
    }

    protected abstract Boolean backgroundTask() throws Exception;
}
