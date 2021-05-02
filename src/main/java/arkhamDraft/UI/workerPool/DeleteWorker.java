package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.awt.*;

public class DeleteWorker extends AbstractWorker {
    private final Runnable draftingBoxWasDiscarded;

    public DeleteWorker(Brain brain, Component parent, Runnable draftingBoxWasDiscarded) {
        super(brain, false, parent);
        this.draftingBoxWasDiscarded = draftingBoxWasDiscarded;
    }

    @Override
    protected void update() {
        draftingBoxWasDiscarded.run();
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        brain.guiDeleteDeck();
        return true;
    }
}
