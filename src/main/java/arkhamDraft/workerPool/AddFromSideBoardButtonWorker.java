package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Function;

public class AddFromSideBoardButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList sideboardList;
    private final Function<Deck, Void> printCardsToDeckPanel;
    private final Function<Deck, Void> printCardsToSideboardPanel;

    public AddFromSideBoardButtonWorker(Brain brain, CardCheckBoxList sideboardList, Function<Deck, Void> printCardsToDeckPanel, Function<Deck, Void> printCardsToSideboardPanel) {
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
        printCardsToDeckPanel.apply(brain.getDraftedDeck());
        printCardsToSideboardPanel.apply(brain.getSideboard());
    }
}
