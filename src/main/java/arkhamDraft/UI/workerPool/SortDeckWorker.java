package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import javax.swing.*;
import java.util.Objects;

public class SortDeckWorker extends AbstractWorker {


    private final JComboBox<String> sortComboBox;
    private final Runnable updateDeckPanel;

    public SortDeckWorker(Brain brain, JComboBox<String> sortComboBox, Runnable updateDeckPanel) {
        super(brain);
        this.sortComboBox = sortComboBox;
        this.updateDeckPanel = updateDeckPanel;
    }


    @Override
    protected void update() {
        updateDeckPanel.run();
    }

    @Override
    protected Boolean doInBackground() {
        brain.sortDeck((String) Objects.requireNonNull(sortComboBox.getSelectedItem()));
        return true;
    }
}
