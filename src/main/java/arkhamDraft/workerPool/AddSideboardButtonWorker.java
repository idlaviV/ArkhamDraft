package arkhamDraft.workerPool;


import arkhamDraft.ArkhamDraftBrain;
import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Consumer;

public class AddSideboardButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;
    private final Runnable printCardsToSideboardPanel;

    public AddSideboardButtonWorker(Brain brain, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel, Runnable printCardsToSideboardPanel) {
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
        printCardsToDraftPanel.run();
        printCardsToSideboardPanel.run();

    }
}
