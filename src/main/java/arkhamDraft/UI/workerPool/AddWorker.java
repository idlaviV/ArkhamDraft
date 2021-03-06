package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

import java.awt.*;

public class AddWorker extends AbstractWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;
    private final Runnable printCardsToDeckPanel;

    public AddWorker(Brain brain, Component parent, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel, Runnable printCardsToDeckPanel) {
        super(brain, false, parent);
        this.draftedCardsList = draftedCardsList;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
        this.printCardsToDeckPanel = printCardsToDeckPanel;
    }

    @Override
    protected Boolean backgroundTask() {
        brain.guiAddToDeck(draftedCardsList.getCheckedCards());
        return true;
    }

    @Override
    protected void update() {
        printCardsToDraftPanel.run();
        printCardsToDeckPanel.run();
    }
}
