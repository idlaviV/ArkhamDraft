package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class DiscardFromSideBoardWorker extends AbstractWorker {
    private final CardCheckBoxList sideBoardList;
    private final Runnable printCardsToSideboardPanel;

    public DiscardFromSideBoardWorker(Brain brain, CardCheckBoxList sideBoardList, Runnable printCardsToSideboardPanel) {
        super(brain);
        this.sideBoardList = sideBoardList;
        this.printCardsToSideboardPanel = printCardsToSideboardPanel;
    }

    @Override
    protected void update() {
        printCardsToSideboardPanel.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiDiscardFromSideboard(sideBoardList.getCheckedCards());
        return true;
    }
}
