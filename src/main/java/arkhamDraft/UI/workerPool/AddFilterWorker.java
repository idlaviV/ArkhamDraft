package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.util.function.Consumer;

public class AddFilterWorker extends AbstractWorker {
    private final Runnable updateFilterListFromBrain;
    private final Consumer<Integer> updateCurrentCardsFiltered;
    private final Runnable addFilterFromUserSelection;

    public AddFilterWorker(Brain brain, Runnable updateFilterListFromBrain, Consumer<Integer> updateCurrentCardsFiltered, Runnable addFilterFromUserSelection) {
        super(brain);
        this.updateFilterListFromBrain = updateFilterListFromBrain;
        this.updateCurrentCardsFiltered = updateCurrentCardsFiltered;
        this.addFilterFromUserSelection = addFilterFromUserSelection;
    }

    @Override
    protected void update() {
        updateFilterListFromBrain.run();
        updateCurrentCardsFiltered.accept(brain.getNumberOfCardsLeftAfterFiltering());
    }

    @Override
    protected Boolean doInBackground() {
        addFilterFromUserSelection.run();
        return true;
    }
}