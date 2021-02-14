package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.Deck;

import java.io.File;
import java.util.function.Consumer;

public class LoadDeckButtonWorker extends AbstractButtonWorker{


    private final File file;
    private final Runnable printCardsToDeckPanel;
    private final Runnable enablers;
    private final Runnable updateOtherPanels;

    public LoadDeckButtonWorker(Brain brain, File file, Runnable printCardsToDeckPanel, Runnable enablers, Runnable updatePanelsAfterwards) {
        super(brain);
        this.file = file;
        this.printCardsToDeckPanel = printCardsToDeckPanel;
        this.enablers = enablers;
        this.updateOtherPanels = updatePanelsAfterwards;
    }

    @Override
    protected void update() {
        printCardsToDeckPanel.run();
        enablers.run();
        updateOtherPanels.run();

    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.guiOpensNewDraftDeckDialog();
        brain.disposeDeck();
        brain.buildDeckFromFile(file);
        brain.clearDrafter();
        return true;
    }
}
