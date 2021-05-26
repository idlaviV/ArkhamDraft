package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.awt.*;

public class NewDraftWorker extends AbstractWorker {
    private final Runnable openDialog;

    public NewDraftWorker(Brain brain, Component parent, Runnable openDialog) {
        super(brain, true, parent);
        this.openDialog = openDialog;
    }

    @Override
    protected void update() {
        openDialog.run();
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        brain.guiCreateNewDeck();
        return true;
    }
}
