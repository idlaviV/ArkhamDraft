package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.Decoder;
import arkhamDraft.UI.HintTextField;

import javax.swing.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class AddFilterButtonWorker extends AbstractButtonWorker{
    private final Runnable updateFilterListFromBrain;
    private final Consumer<Integer> updateCurrentCardsFiltered;
    private final Runnable addFilterFromUserSelection;

    public AddFilterButtonWorker(Brain brain, Runnable updateFilterListFromBrain, Consumer<Integer> updateCurrentCardsFiltered, Runnable addFilterFromUserSelection) {
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
