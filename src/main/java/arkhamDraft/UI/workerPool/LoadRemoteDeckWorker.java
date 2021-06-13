package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class LoadRemoteDeckWorker extends AbstractWorker{
    private final Runnable printCardsToDeckPanel;
    private final Runnable enablers;
    private final Runnable updateOtherPanels;
    private final Function<Object, String> checkIfArkhamDBdeckIdIsValid;
    private boolean loadSuccessful = false;

    public LoadRemoteDeckWorker(Brain brain, Component parent, Function<Object, String> checkIfArkhamDBdeckIdIsValid, Runnable printCardsToDeckPanel, Runnable enablers, Runnable updatePanelsAfterwards) {
        super(brain, true, parent);
        this.checkIfArkhamDBdeckIdIsValid = checkIfArkhamDBdeckIdIsValid;
        this.printCardsToDeckPanel = printCardsToDeckPanel;
        this.enablers = enablers;
        this.updateOtherPanels = updatePanelsAfterwards;
    }

    @Override
    protected void update() {
        if (loadSuccessful) {
            System.out.println("Load successful");
            printCardsToDeckPanel.run();
            enablers.run();
            updateOtherPanels.run();
        }
    }

    @Override
    protected Boolean backgroundTask() throws Exception {
        Object value = JOptionPane.showInputDialog(
                null,
                "Please pick an ArkhamDB-id.",
                "Load deck from ArkhamDB",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "id"
        );
        String userInput = checkIfArkhamDBdeckIdIsValid.apply(value);
        if (userInput != null) {
            loadSuccessful = brain.loadDeckFromArkhamDBiD(userInput);
            if (!loadSuccessful) {
                JOptionPane.showMessageDialog(parent, "Deck id could not be read.", "Deck not found", JOptionPane.WARNING_MESSAGE);
            }
        }
        return true;
    }
}
