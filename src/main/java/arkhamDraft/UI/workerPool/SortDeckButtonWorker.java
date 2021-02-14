package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import javax.swing.*;
import java.util.Objects;

public class SortDeckButtonWorker extends AbstractButtonWorker{


    private final JComboBox<String> sortComboBox;
    private final Runnable printCardsToDeckPanel;

    public SortDeckButtonWorker(Brain brain, JComboBox<String> sortComboBox, Runnable printCardsToDeckPanel) {
        super(brain);
        this.sortComboBox = sortComboBox;
        this.printCardsToDeckPanel = printCardsToDeckPanel;
    }


    @Override
    protected void update() {
        printCardsToDeckPanel.run();
    }

    @Override
    protected Boolean doInBackground() {
        brain.sortDeck((String) Objects.requireNonNull(sortComboBox.getSelectedItem()));
        return true;
    }
}
