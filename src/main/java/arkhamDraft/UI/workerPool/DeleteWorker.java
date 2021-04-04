package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

public class DeleteWorker extends AbstractWorker {
    private final Runnable draftingBoxWasDiscarded;

    public DeleteWorker(Brain brain, Runnable draftingBoxWasDiscarded) {
        super(brain);
        this.draftingBoxWasDiscarded = draftingBoxWasDiscarded;
    }

    @Override
    protected void update() {
        draftingBoxWasDiscarded.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiDeleteDeck();
        return true;
    }
}
