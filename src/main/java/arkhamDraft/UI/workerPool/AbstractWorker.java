package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.DeckSaver;
import arkhamDraft.UI.MyFileChooser;
import arkhamDraft.UI.SavePromptAsker;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public abstract class AbstractWorker extends SwingWorker<Boolean, Void> {

    protected final Brain brain;
    private final boolean checkChangedFlag;
    protected final Component parent;

    public AbstractWorker(Brain brain, boolean checkChangedFlag, Component parent) {
        this.brain = brain;
        this.checkChangedFlag = checkChangedFlag;
        this.parent = parent;
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
            int promptValue = SavePromptAsker.promptUser();
            if (promptValue == SavePromptAsker.SAVE_NO) {
                return backgroundTask();
            }
            if (promptValue == SavePromptAsker.CANCEL) {
                return false;
            }
            if (promptValue == SavePromptAsker.SAVE_YES) {
                if (DeckSaver.saveDeck(new MyFileChooser(), parent, brain)) {
                    return backgroundTask();
                }
                return false;
            }
        }
        return backgroundTask();
    }


    protected abstract Boolean backgroundTask() throws Exception;
}
