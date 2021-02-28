package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

public class FinalizeDraftDeckButtonWorker extends AbstractButtonWorker{
    private final Runnable dispose;
    private final Runnable printCardsToDraftPanel;
    private final Runnable updateLabelCurrentCardsInDraftingDeck;
    private final Runnable enableDraft;

    public FinalizeDraftDeckButtonWorker(Brain brain, Runnable dispose, Runnable printCardsToDraftPanel, Runnable updateLabelCurrentCardsInDraftingDeck, Runnable enableDraft) {
        super(brain);
        this.dispose = dispose;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.updateLabelCurrentCardsInDraftingDeck = updateLabelCurrentCardsInDraftingDeck;
        this.enableDraft = enableDraft;
    }

    @Override
    protected void update() {
        printCardsToDraftPanel.run();
        enableDraft.run();
        updateLabelCurrentCardsInDraftingDeck.run();
        dispose.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiFinalizeDraftDeck();
        return true;
    }
}
