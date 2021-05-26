package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.awt.*;

public class OpenNewDraftDeckDialogWorker extends AbstractWorker {

    private final Runnable openNewDraftDeckDialog;

    public OpenNewDraftDeckDialogWorker(Brain brain, Component parent, Runnable openNewDraftDeckDialog) {
        super(brain, false, parent);
        this.openNewDraftDeckDialog = openNewDraftDeckDialog;
    }

    @Override
    protected void update() {
        openNewDraftDeckDialog.run();
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        brain.guiOpensNewDraftDeckDialog();
        return true;
    }
}
