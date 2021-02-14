package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Consumer;

public class DiscardFromSideBoardButtonWorker extends AbstractButtonWorker{
    private final CardCheckBoxList sideBoardList;
    private final Runnable printCardsToSideboardPanel;

    public DiscardFromSideBoardButtonWorker(Brain brain, CardCheckBoxList sideBoardList, Runnable printCardsToSideboardPanel) {
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
