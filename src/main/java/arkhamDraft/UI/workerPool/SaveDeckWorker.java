package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.awt.*;
import java.io.File;

public class SaveDeckWorker extends AbstractWorker {


    private final File file;

    public SaveDeckWorker(Brain brain, Component parent, File file) {
        super(brain, false, parent);
        this.file = file;
    }

    @Override
    protected void update() {

    }

    @Override
    protected Boolean backgroundTask() throws Exception {
            brain.guiSaveDeck(file);
        return true;
    }
}
