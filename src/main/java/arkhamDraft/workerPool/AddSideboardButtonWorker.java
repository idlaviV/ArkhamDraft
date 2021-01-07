package arkhamDraft.workerPool;


import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Function;

public class AddSideboardButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Function<Deck, Void> printCardsToDraftPanel;
    private final Function<Deck, Void> printCardsToSideboardPanel;

    public AddSideboardButtonWorker(Brain brain, CardCheckBoxList draftedCardsList, Function<Deck, Void> printCardsToDraftPanel, Function<Deck, Void> printCardsToSideboardPanel) {
        super(brain);
        this.draftedCardsList = draftedCardsList;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.printCardsToSideboardPanel = printCardsToSideboardPanel;
    }

    @Override
    protected Boolean doInBackground() {
        brain.guiAddToSideboard(draftedCardsList.getCheckedCards());
        return true;
    }

    // Can safely update the GUI from this method.
    protected void update() {
        printCardsToDraftPanel.apply(brain.getDraftedCards());
        printCardsToSideboardPanel.apply(brain.getSideboard());

    }
}
