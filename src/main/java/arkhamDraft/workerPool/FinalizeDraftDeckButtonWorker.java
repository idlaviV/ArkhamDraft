package arkhamDraft.workerPool;

import arkhamDraft.ArkhamDraftBrain;
import arkhamDraft.Brain;
import arkhamDraft.Deck;

import java.util.function.Consumer;

public class FinalizeDraftDeckButtonWorker extends AbstractButtonWorker{
    private final Runnable dispose;
    private final Runnable printCardsToDraftPanel;
    private final Runnable enableDraft;

    public FinalizeDraftDeckButtonWorker(Brain brain, Runnable dispose, Runnable printCardsToDraftPanel, Runnable enableDraft) {
        super(brain);
        this.dispose = dispose;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.enableDraft = enableDraft;
    }

    @Override
    protected void update() {
        printCardsToDraftPanel.run();
        enableDraft.run();
        dispose.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiFinalizeDraftDeck();
        return true;
    }
}
