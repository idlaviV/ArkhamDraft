package arkhamDraft.workerPool;

import arkhamDraft.ArkhamDraftBrain;
import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Consumer;

public class RedraftButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Consumer<Deck> printCardsToDraftPanel;

    public RedraftButtonWorker(Brain brain, CardCheckBoxList draftedCardsList, Consumer<Deck> printCardsToDraftPanel) {
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
        printCardsToDraftPanel.accept(brain.getDraftedCards());
    }
}
