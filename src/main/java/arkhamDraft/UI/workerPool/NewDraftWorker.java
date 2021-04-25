package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

public class NewDraftWorker extends AbstractWorker {
    private final Runnable openDialog;

    public NewDraftWorker(Brain brain, Runnable openDialog) {
        super(brain, true);
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
