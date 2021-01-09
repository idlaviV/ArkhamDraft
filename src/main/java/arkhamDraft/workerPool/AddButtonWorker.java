package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Consumer;
import java.util.function.Function;

public class AddButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Consumer<Deck> printCardsToDraftPanel;
    private final Consumer<Deck> printCardsToDeckPanel;

    public AddButtonWorker(Brain brain, CardCheckBoxList draftedCardsList, Consumer<Deck> printCardsToDraftPanel, Consumer<Deck> printCardsToDeckPanel) {
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
        printCardsToDraftPanel.accept(brain.getDraftedCards());
        printCardsToDeckPanel.accept(brain.getDraftedDeck());
    }
}
