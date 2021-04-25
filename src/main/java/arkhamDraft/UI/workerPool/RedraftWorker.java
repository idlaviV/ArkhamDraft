package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class RedraftWorker extends AbstractWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;

    public RedraftWorker(Brain brain, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel) {
        super(brain, false);
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
