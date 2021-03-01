package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.UI.CardCheckBoxList;

public class RemoveCardFromDeckButtonWorker extends AbstractButtonWorker{

    private final Runnable updateAllPanels;
    private final CardCheckBoxList deckList;

    public RemoveCardFromDeckButtonWorker(Brain brain, Runnable updateAllPanels, CardCheckBoxList deckList) {
        super(brain);
        this.updateAllPanels = updateAllPanels;
        this.deckList = deckList;
    }

    @Override
    protected void update() {
        updateAllPanels.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiDiscardFromDraftedDeck(deckList.getCheckedCards());
        return true;
    }
}
