package arkhamDraft.workerPool;

import arkhamDraft.ArkhamDraftBrain;
import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.BiConsumer;

public class DraftCardsButtonWorker extends AbstractButtonWorker {
    private final int numberOfDraftedCards;
    private final BiConsumer<Deck, CardCheckBoxList> printCardsToPanel;
    private final CardCheckBoxList panel;

    public DraftCardsButtonWorker(Brain brain, int numberOfDraftedCards, BiConsumer<Deck, CardCheckBoxList> printCardsToPanel, CardCheckBoxList panel) {
        super(brain);
        this.numberOfDraftedCards = numberOfDraftedCards;
        this.printCardsToPanel = printCardsToPanel;
        this.panel = panel;
    }

    @Override
    protected Boolean doInBackground() {
        brain.guiDraftCardsNew(numberOfDraftedCards);
        return true;
    }

    @Override
    protected void update() {
        printCardsToPanel.accept(brain.getDraftedCards(), panel);
    }
}
