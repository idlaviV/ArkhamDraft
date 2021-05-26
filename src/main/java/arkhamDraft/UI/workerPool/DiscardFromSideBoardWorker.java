package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

import java.awt.*;

public class DiscardFromSideBoardWorker extends AbstractWorker {
    private final CardCheckBoxList sideBoardList;
    private final Runnable printCardsToSideboardPanel;

    public DiscardFromSideBoardWorker(Brain brain, Component parent, CardCheckBoxList sideBoardList, Runnable printCardsToSideboardPanel) {
        super(brain, false, parent);
        this.sideBoardList = sideBoardList;
        this.printCardsToSideboardPanel = printCardsToSideboardPanel;
    }

    @Override
    protected void update() {
        printCardsToSideboardPanel.run();
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        brain.guiDiscardFromSideboard(sideBoardList.getCheckedCards());
        return true;
    }
}
