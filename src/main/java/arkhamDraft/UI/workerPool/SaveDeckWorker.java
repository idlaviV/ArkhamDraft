package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.io.File;

public class SaveDeckWorker extends AbstractWorker {


    private final File file;

    public SaveDeckWorker(Brain brain, File file) {
        super(brain);
        this.file = file;
    }

    @Override
    protected void update() {

    }

    @Override
    protected Boolean doInBackground() throws Exception {
            brain.guiSaveDeck(file);
        return true;
    }
}
