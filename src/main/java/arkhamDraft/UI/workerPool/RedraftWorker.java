package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

import java.awt.*;

public class RedraftWorker extends AbstractWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;

    public RedraftWorker(Brain brain, Component parent, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel) {
        super(brain, false, parent);
        this.draftedCardsList = draftedCardsList;
        this.printCardsToDraftPanel = printCardsToDraftPanel;
    }

    @Override
    protected Boolean backgroundTask() {
        brain.guiRedraft(draftedCardsList.getCheckedCards());
        return true;
    }

    protected void update() {
        printCardsToDraftPanel.run();
    }
}
