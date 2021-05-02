package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

import java.awt.*;

public class RemoveCardFromDeckWorker extends AbstractWorker {

    private final Runnable updateAllPanels;
    private final CardCheckBoxList deckList;

    public RemoveCardFromDeckWorker(Brain brain, Component parent, Runnable updateAllPanels, CardCheckBoxList deckList) {
        super(brain, false, parent);
        this.updateAllPanels = updateAllPanels;
        this.deckList = deckList;
    }

    @Override
    protected void update() {
        updateAllPanels.run();
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        brain.guiDiscardFromDraftedDeck(deckList.getCheckedCards());
        return true;
    }
}
