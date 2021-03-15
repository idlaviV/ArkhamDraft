package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import javax.swing.*;
import java.util.Objects;

public class SortDeckButtonWorker extends AbstractButtonWorker{


    private final JComboBox<String> sortComboBox;
    private final Runnable updateDeckPanel;

    public SortDeckButtonWorker(Brain brain, JComboBox<String> sortComboBox, Runnable updateDeckPanel) {
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
