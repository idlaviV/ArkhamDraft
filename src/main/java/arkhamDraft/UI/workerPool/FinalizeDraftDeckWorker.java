package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

public class FinalizeDraftDeckWorker extends AbstractWorker {
    private final Runnable dispose;
    private final Runnable updateAllPanels;
    private final Runnable enableDraft;

    public FinalizeDraftDeckWorker(Brain brain, Runnable dispose, Runnable updateAllPanels, Runnable enableDraft) {
        super(brain);
        this.dispose = dispose;
        this.updateAllPanels = updateAllPanels;
        this.enableDraft = enableDraft;
    }

    @Override
    protected void update() {
        updateAllPanels.run();
        enableDraft.run();
        dispose.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiFinalizeDraftDeck();
        return true;
    }
}