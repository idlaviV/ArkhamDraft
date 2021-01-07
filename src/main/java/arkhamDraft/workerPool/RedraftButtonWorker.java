package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Function;

public class RedraftButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Function<Deck, Void> printCardsToDraftPanel;

    public RedraftButtonWorker(Brain brain, CardCheckBoxList draftedCardsList, Function<Deck, Void> printCardsToDraftPanel) {
        super(brain);
        this.draftedCardsList = draftedCardsList;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
    }

    @Override
    protected Boolean doInBackground() {
        brain.guiRedraft(draftedCardsList.getCheckedCards());
        return true;
    }

    protected void update() {
        printCardsToDraftPanel.apply(brain.getDraftedCards());
    }
}
