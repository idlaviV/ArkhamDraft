package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import javax.swing.*;
import java.util.Objects;

public class SortDraftedCardsWorker extends AbstractWorker {
    private final JComboBox<String> sortComboBox;
    private final Runnable updateDraftingAndSideboardPanel;

    public SortDraftedCardsWorker(Brain brain, JComboBox<String> sortComboBox, Runnable updateDraftingAndSideboardPanel) {
        super(brain, false);
        this.sortComboBox = sortComboBox;
        this.updateDraftingAndSideboardPanel = updateDraftingAndSideboardPanel;
    }

    @Override
    protected void update() {
        updateDraftingAndSideboardPanel.run();
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        brain.sortDraftedCards((String) Objects.requireNonNull(sortComboBox.getSelectedItem()));
        return true;
    }
}
