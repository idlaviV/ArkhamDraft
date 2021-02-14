package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class RedraftButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;

    public RedraftButtonWorker(Brain brain, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel) {
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
