package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

public class OpenNewDraftDeckDialogWorker extends AbstractWorker {

    private final Runnable openNewDraftDeckDialog;

    public OpenNewDraftDeckDialogWorker(Brain brain, Runnable openNewDraftDeckDialog) {
        super(brain, false);
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
