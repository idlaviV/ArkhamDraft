package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.NewDraftDeckDialog;

public class OpenNewDraftDeckDialogWorker extends AbstractWorker {

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
