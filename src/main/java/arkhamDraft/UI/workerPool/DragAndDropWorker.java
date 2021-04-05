package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.Card;
import arkhamDraft.CardPanel;

public class DragAndDropWorker extends AbstractWorker {
    private final CardPanel from;
    private final CardPanel to;
    private final Card card;
    private final int row;
    private final Runnable updateAllPanels;

    public DragAndDropWorker(Brain brain, CardPanel from, CardPanel to, Card card, int row, Runnable updateAllPanels) {
        super(brain);
        this.from = from;
        this.to = to;
        this.card = card;
        this.row = row;
        this.updateAllPanels = updateAllPanels;
    }

    @Override
    protected void update() {
        updateAllPanels.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        if (brain.guiInsertCardFromPanelToPanel(from, to, card, row)) {
            return true;
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
