package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.CardCheckBoxList;
import arkhamDraft.Deck;

import java.util.function.BiFunction;

public class DraftCardsButtonWorker extends AbstractButtonWorker {
    private final int numberOfDraftedCards;
    private final BiFunction<Deck, CardCheckBoxList, Void> printCardsToPanel;
    private final CardCheckBoxList panel;

    public DraftCardsButtonWorker(Brain brain, int numberOfDraftedCards, BiFunction<Deck, CardCheckBoxList, Void> printCardsToPanel, CardCheckBoxList panel) {
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
        printCardsToPanel.apply(brain.getDraftedCards(), panel);
    }
}
