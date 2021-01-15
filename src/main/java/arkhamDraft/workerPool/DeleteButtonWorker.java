package arkhamDraft.workerPool;

import arkhamDraft.Brain;

public class DeleteButtonWorker extends AbstractButtonWorker{
    private final Runnable draftingBoxWasDiscarded;

    public DeleteButtonWorker(Brain brain, Runnable draftingBoxWasDiscarded) {
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
