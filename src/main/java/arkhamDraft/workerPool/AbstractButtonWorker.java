package arkhamDraft.workerPool;

import arkhamDraft.Brain;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

public abstract class AbstractButtonWorker extends SwingWorker<Boolean, Void> {

    protected final Brain brain;

    public AbstractButtonWorker(Brain brain) {
        this.brain = brain;
    }

    protected void done() {

        boolean status;
        try {
            // Retrieve the return value of doInBackground.
            status = get();
            if (status) {
                update();
            }
        } catch (InterruptedException e) {
            // This is thrown if the thread's interrupted.
        } catch (ExecutionException e) {
            // This is thrown if we throw an exception
            // from doInBackground.
        }
    }

    protected abstract void update();
}
