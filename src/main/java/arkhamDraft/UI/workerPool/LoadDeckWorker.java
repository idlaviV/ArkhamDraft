package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.io.File;

public class LoadDeckWorker extends AbstractWorker {


    private final File file;
    private final Runnable printCardsToDeckPanel;
    private final Runnable enablers;
    private final Runnable updateOtherPanels;

    public LoadDeckWorker(Brain brain, File file, Runnable printCardsToDeckPanel, Runnable enablers, Runnable updatePanelsAfterwards) {
        super(brain, true);
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
    protected Boolean backgroundTask() throws Exception {
        brain.guiOpensNewDraftDeckDialog();
        brain.guiFinalizeDraftDeck();
        brain.disposeDeck();
        brain.buildDeckFromFile(file);
        return true;
    }
}
