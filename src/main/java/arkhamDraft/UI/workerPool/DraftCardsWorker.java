package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.awt.*;

public class DraftCardsWorker extends AbstractWorker {
    private final int numberOfDraftedCards;
    private final Runnable printCardsToDraftPanel;
    private final Runnable updateLabelCurrentNumberOfCardsInDraftingDeck;

    public DraftCardsWorker(Brain brain, Component parent, int numberOfDraftedCards, Runnable printCardsToDraftPanel, Runnable updateLabelCurrentNumberOfCardsInDraftingDeck) {
        super(brain, false, parent);
        this.numberOfDraftedCards = numberOfDraftedCards;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.updateLabelCurrentNumberOfCardsInDraftingDeck = updateLabelCurrentNumberOfCardsInDraftingDeck;
    }

    @Override
    protected Boolean backgroundTask() {
        brain.guiDraftCardsNew(numberOfDraftedCards);
        return true;
    }

    @Override
    protected void update() {
        printCardsToDraftPanel.run();
        updateLabelCurrentNumberOfCardsInDraftingDeck.run();
    }
}
