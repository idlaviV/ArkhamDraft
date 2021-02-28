package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.BiConsumer;

public class DraftCardsButtonWorker extends AbstractButtonWorker {
    private final int numberOfDraftedCards;
    private final Runnable printCardsToDraftPanel;
    private final Runnable updateLabelCurrentNumberOfCardsInDraftingDeck;
    private final CardCheckBoxList panel;

    public DraftCardsButtonWorker(Brain brain, int numberOfDraftedCards, Runnable printCardsToDraftPanel, Runnable updateLabelCurrentNumberOfCardsInDraftingDeck, CardCheckBoxList panel) {
        super(brain);
        this.numberOfDraftedCards = numberOfDraftedCards;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.updateLabelCurrentNumberOfCardsInDraftingDeck = updateLabelCurrentNumberOfCardsInDraftingDeck;
        this.panel = panel;
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
