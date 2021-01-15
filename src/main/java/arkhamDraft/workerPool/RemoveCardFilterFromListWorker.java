package arkhamDraft.workerPool;

import arkhamDraft.ArkhamDraftBrain;
import arkhamDraft.Brain;
import arkhamDraft.CardFilter;

import java.util.function.Consumer;

public class RemoveCardFilterFromListWorker extends AbstractButtonWorker{
    private final CardFilter cardFilter;
    private final Runnable revalidate;
    private final Runnable repaint;
    private final Consumer<Integer> updateCurrentCardsFiltered;

    public RemoveCardFilterFromListWorker(Brain brain, CardFilter cardFilter, Runnable revalidate, Runnable repaint, Consumer<Integer> updateCurrentCardsFiltered) {
        super(brain);
        this.cardFilter = cardFilter;
        this.revalidate = revalidate;
        this.repaint = repaint;
        this.updateCurrentCardsFiltered = updateCurrentCardsFiltered;
    }

    @Override
    protected void update() {
        revalidate.run();
        repaint.run();
        updateCurrentCardsFiltered.accept(brain.getNumberOfCardsLeftAfterFiltering());
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.removeCardFilterFromList(cardFilter);
        return true;
    }
}
