package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

public class DraftCardsWorker extends AbstractWorker {
    private final int numberOfDraftedCards;
    private final Runnable printCardsToDraftPanel;
    private final Runnable updateLabelCurrentNumberOfCardsInDraftingDeck;

    public DraftCardsWorker(Brain brain, int numberOfDraftedCards, Runnable printCardsToDraftPanel, Runnable updateLabelCurrentNumberOfCardsInDraftingDeck) {
        super(brain);
        this.numberOfDraftedCards = numberOfDraftedCards;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.updateLabelCurrentNumberOfCardsInDraftingDeck = updateLabelCurrentNumberOfCardsInDraftingDeck;
    }

    @Override
    protected Boolean doInBackground() {
        brain.guiDraftCardsNew(numberOfDraftedCards);
        return true;
    }

    @Override
    protected void update() {
        printCardsToDraftPanel.run();
        updateLabelCurrentNumberOfCardsInDraftingDeck.run();
    }
}
