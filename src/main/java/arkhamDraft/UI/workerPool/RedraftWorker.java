package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class RedraftWorker extends AbstractWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;

    public RedraftWorker(Brain brain, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel) {
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
        printCardsToDraftPanel.run();
    }
}
