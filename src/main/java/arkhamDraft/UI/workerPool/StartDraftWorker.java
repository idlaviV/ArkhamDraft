package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

public class StartDraftWorker extends AbstractWorker {
    private final Runnable openDialog;

    public StartDraftWorker(Brain brain, Runnable openDialog) {
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
