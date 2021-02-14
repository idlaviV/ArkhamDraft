package arkhamDraft.UI.workerPool;

import arkhamDraft.Brain;

import java.io.File;

public class SaveDeckButtonWorker extends AbstractButtonWorker{


    private final File file;

    public SaveDeckButtonWorker(Brain brain, File file) {
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
