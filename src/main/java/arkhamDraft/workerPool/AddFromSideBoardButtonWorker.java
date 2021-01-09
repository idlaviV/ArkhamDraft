package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.Consumer;
import java.util.function.Function;

public class AddFromSideBoardButtonWorker extends AbstractButtonWorker {
    private final CardCheckBoxList sideboardList;
    private final Consumer<Deck> printCardsToDeckPanel;
    private final Consumer<Deck> printCardsToSideboardPanel;

    public AddFromSideBoardButtonWorker(Brain brain, CardCheckBoxList sideboardList, Consumer<Deck> printCardsToDeckPanel, Consumer<Deck> printCardsToSideboardPanel) {
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
        printCardsToDeckPanel.accept(brain.getDraftedDeck());
        printCardsToSideboardPanel.accept(brain.getSideboard());
    }
}
