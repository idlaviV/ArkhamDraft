package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Function;

public class AddButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Function<Deck, Void> printCardsToDraftPanel;
    private final Function<Deck, Void> printCardsToDeckPanel;

    public AddButtonWorker(Brain brain, CardCheckBoxList draftedCardsList, Function<Deck, Void> printCardsToDraftPanel, Function<Deck, Void> printCardsToDeckPanel) {
        super(brain);
        this.draftedCardsList = draftedCardsList;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.printCardsToDeckPanel = printCardsToDeckPanel;
    }

    @Override
    protected Boolean doInBackground() {
        brain.guiAddToDeck(draftedCardsList.getCheckedCards());
        return true;
    }

    @Override
    protected void update() {
        printCardsToDraftPanel.apply(brain.getDraftedCards());
        printCardsToDeckPanel.apply(brain.getDraftedDeck());
    }
}
