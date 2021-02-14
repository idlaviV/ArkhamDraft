package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class AddButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;
    private final Runnable printCardsToDeckPanel;

    public AddButtonWorker(Brain brain, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel, Runnable printCardsToDeckPanel) {
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
        printCardsToDraftPanel.run();
        printCardsToDeckPanel.run();
    }
}
