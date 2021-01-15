package arkhamDraft.workerPool;

import arkhamDraft.ArkhamDraftBrain;
import arkhamDraft.Brain;
import arkhamDraft.Decoder;
import arkhamDraft.HintTextField;

import javax.swing.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class AddFilterButtonWorker extends AbstractButtonWorker{
    private final JComboBox<String> attributeSelector;
    private final JComboBox<String> relatorSelector;
    private final HintTextField valueSelector;
    private final Runnable updateFilterListFromBrain;
    private final Consumer<Integer> updateCurrentCardsFiltered;

    public AddFilterButtonWorker(Brain brain, JComboBox<String> attributeSelector, JComboBox<String> relatorSelector, HintTextField valueSelector, Runnable updateFilterListFromBrain, Consumer<Integer> updateCurrentCardsFiltered) {
        super(brain);
        this.attributeSelector = attributeSelector;
        this.relatorSelector = relatorSelector;
        this.valueSelector = valueSelector;
        this.updateFilterListFromBrain = updateFilterListFromBrain;
        this.updateCurrentCardsFiltered = updateCurrentCardsFiltered;
    }

    @Override
    protected void update() {
        updateFilterListFromBrain.run();
        updateCurrentCardsFiltered.accept(brain.getNumberOfCardsLeftAfterFiltering());
    }

    @Override
    protected Boolean doInBackground() {
        ArrayList<String> arguments = Decoder.decryptGUIFilter(attributeSelector.getItemAt(attributeSelector.getSelectedIndex()),
                relatorSelector.getItemAt(relatorSelector.getSelectedIndex()),
                valueSelector.getText());
        brain.addFilterFromGUI(arguments);
        return true;
    }
}
