package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Consumer;

public class DiscardFromSideBoardButtonWorker extends AbstractButtonWorker{
    private final CardCheckBoxList sideBoardList;
    private final Consumer<Deck> printCardsToSideboardPanel;

    public DiscardFromSideBoardButtonWorker(Brain brain, CardCheckBoxList sideBoardList, Consumer<Deck> printCardsToSideboardPanel) {
        super(brain);
        this.sideBoardList = sideBoardList;
        this.printCardsToSideboardPanel = printCardsToSideboardPanel;
    }

    @Override
    protected void update() {
        printCardsToSideboardPanel.accept(brain.getSideboard());
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiDiscardFromSideboard(sideBoardList.getCheckedCards());
        return true;
    }
}
