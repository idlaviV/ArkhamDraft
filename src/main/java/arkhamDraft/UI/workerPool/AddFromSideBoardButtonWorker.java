package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class AddFromSideBoardButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList sideboardList;
    private final Runnable printCardsToDeckPanel;
    private final Runnable printCardsToSideboardPanel;

    public AddFromSideBoardButtonWorker(Brain brain, CardCheckBoxList sideboardList, Runnable printCardsToDeckPanel, Runnable printCardsToSideboardPanel) {
        super(brain);
        this.sideboardList = sideboardList;
        this.printCardsToDeckPanel = printCardsToDeckPanel;
        this.printCardsToSideboardPanel = printCardsToSideboardPanel;
    }

    @Override
    protected Boolean doInBackground() {
        brain.guiAddFromSideboard(sideboardList.getCheckedCards());
        return true;
    }

    @Override
    protected void update() {
        printCardsToDeckPanel.run();
        printCardsToSideboardPanel.run();
    }
}
