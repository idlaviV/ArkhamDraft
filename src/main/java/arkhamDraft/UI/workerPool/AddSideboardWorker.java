package arkhamDraft.UI.workerPool;


import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class AddSideboardWorker extends AbstractWorker {
    private final CardCheckBoxList draftedCardsList;
    private final Runnable printCardsToDraftPanel;
    private final Runnable printCardsToSideboardPanel;

    public AddSideboardWorker(Brain brain, CardCheckBoxList draftedCardsList, Runnable printCardsToDraftPanel, Runnable printCardsToSideboardPanel) {
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