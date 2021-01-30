package arkhamDraft.workerPool;

import arkhamDraft.Brain;
import arkhamDraft.Deck;

import java.io.File;
import java.util.function.Consumer;

public class LoadDeckButtonWorker extends AbstractButtonWorker{


    private final File file;
    private final Consumer<Deck> printCardsToDeckPanel;

    public LoadDeckButtonWorker(Brain brain, File file, Consumer<Deck> printCardsToDeckPanel) {
        super(brain);
        this.file = file;
        this.printCardsToDeckPanel = printCardsToDeckPanel;
    }

    @Override
    protected void update() {
        printCardsToDeckPanel.accept(brain.getDraftedDeck());
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        brain.disposeDeck();
        brain.buildDeckFromFile(file);
        return true;
    }
}
