package arkhamDraft.workerPool;

import arkhamDraft.ArkhamDraftBrain;
import arkhamDraft.Brain;
import arkhamDraft.Deck;

import java.util.function.Consumer;

public class FinalizeDraftDeckButtonWorker extends AbstractButtonWorker{
    private final Runnable dispose;
    private final Consumer<Deck> printCardsToDraftPanel;
    private final Runnable enableDraft;

    public FinalizeDraftDeckButtonWorker(Brain brain, Runnable dispose, Consumer<Deck> printCardsToDraftPanel, Runnable enableDraft) {
        super(brain);
        this.dispose = dispose;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.enableDraft = enableDraft;
    }

    @Override
    protected void update() {
        printCardsToDraftPanel.accept(brain.getDraftedCards());
        enableDraft.run();
        dispose.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiFinalizeDraftDeck();
        return true;
    }
}
