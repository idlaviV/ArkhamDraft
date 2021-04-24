package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

public class CloseNewDraftDeckDialogWorker extends AbstractWorker {

    public CloseNewDraftDeckDialogWorker(Brain brain) {
        super(brain);
    }

    @Override
    protected void update() {

    }

    @Override
    protected Boolean doInBackground() throws Exception {
        //brain.guiClosesNewDraftDeckDialog();
        return true;
    }
}
