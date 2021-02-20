package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.NewDraftDeckDialog;
import arkhamDraft.UI.workerPool.AbstractButtonWorker;

public class OpenNewDraftDeckDialogWorker extends AbstractButtonWorker {

    private final NewDraftDeckDialog newDraftDeckDialog;

    public OpenNewDraftDeckDialogWorker(Brain brain, NewDraftDeckDialog newDraftDeckDialog) {
        super(brain);
        this.newDraftDeckDialog = newDraftDeckDialog;
    }

    @Override
    protected void update() {
        newDraftDeckDialog.tidyUp();
        newDraftDeckDialog.setVisible(true);
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiOpensNewDraftDeckDialog();
        return true;
    }
}
