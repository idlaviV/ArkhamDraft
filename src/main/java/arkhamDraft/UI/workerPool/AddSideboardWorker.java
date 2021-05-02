package arkhamDraft.UI.workerPool;


import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

import java.awt.*;

public class AddSideboardWorker extends AbstractWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;
    private final Runnable printCardsToSideboardPanel;

    public AddSideboardWorker(Brain brain, Component parent, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel, Runnable printCardsToSideboardPanel) {
        super(brain, false, parent);
        this.draftedCardsList = draftedCardsList;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.printCardsToSideboardPanel = printCardsToSideboardPanel;
    }

    @Override
    protected Boolean backgroundTask() {
        brain.guiAddToSideboard(draftedCardsList.getCheckedCards());
        return true;
    }

    // Can safely update the GUI from this method.
    protected void update() {
        printCardsToDraftPanel.run();
        printCardsToSideboardPanel.run();

    }
}
