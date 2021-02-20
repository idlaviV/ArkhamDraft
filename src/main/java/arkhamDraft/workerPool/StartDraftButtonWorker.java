package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.workerPool.AbstractButtonWorker;

import java.awt.event.ActionListener;

public class StartDraftButtonWorker extends AbstractButtonWorker {
    private final Runnable openDialog;

    public StartDraftButtonWorker(Brain brain, Runnable openDialog) {
        super(brain);
        this.openDialog = openDialog;
    }

    @Override
    protected void update() {
        openDialog.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiCreateNewDeck();
        return true;
    }
}
