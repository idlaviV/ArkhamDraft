package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import javax.swing.*;
import java.util.Objects;

public class SortDraftedCardsButtonWorker extends AbstractButtonWorker{
    private final JComboBox<String> sortComboBox;
    private final Runnable updateDraftingAndSideboardPanel;

    public SortDraftedCardsButtonWorker(Brain brain, JComboBox<String> sortComboBox, Runnable updateDraftingAndSideboardPanel) {
        super(brain);
        this.sortComboBox = sortComboBox;
        this.updateDraftingAndSideboardPanel = updateDraftingAndSideboardPanel;
    }

    @Override
    protected void update() {
        updateDraftingAndSideboardPanel.run();
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.sortDraftedCards((String) Objects.requireNonNull(sortComboBox.getSelectedItem()));
        return true;
    }
}
