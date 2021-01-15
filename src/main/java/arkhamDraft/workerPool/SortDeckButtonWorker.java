package arkhamDraft.workerPool;

import arkhamDraft.ArkhamDraftBrain;
import arkhamDraft.Brain;
import arkhamDraft.Deck;

import javax.swing.*;
import java.util.Objects;
import java.util.function.Function;

public class SortDeckButtonWorker extends AbstractButtonWorker{


    private final JComboBox<String> sortComboBox;
    private final Function<Deck, Void> printCardsToDeckPanel;

    public SortDeckButtonWorker(Brain brain, JComboBox<String> sortComboBox, Function<Deck, Void> printCardsToDeckPanel) {
        super(brain);
        this.sortComboBox = sortComboBox;
        this.printCardsToDeckPanel = printCardsToDeckPanel;
    }


    @Override
    protected void update() {
        printCardsToDeckPanel.apply(brain.getDraftedDeck());
    }

    @Override
    protected Boolean doInBackground() {
        brain.sortDeck((String) Objects.requireNonNull(sortComboBox.getSelectedItem()));
        return true;
    }
}
