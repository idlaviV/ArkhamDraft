package arkhamDraft.UI.workerPool;

import arkhamDraft.SettingsManager;

import java.io.File;
import java.util.function.Consumer;

public class applySettingsButtonWorker extends AbstractButtonWorker{
    private final SettingsManager manager;
    private final boolean regularCards;
    private final Consumer<Boolean> changesWereMaid;

    public applySettingsButtonWorker(SettingsManager manager, boolean regularCards, Consumer<Boolean> changesWereMaid) {
        super(null);
        this.manager = manager;
        this.regularCards = regularCards;
        this.changesWereMaid = changesWereMaid;
    }

    @Override
    protected void update() {
        changesWereMaid.accept(false);
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        manager.toggleRegular(regularCards);
        return true;
    }
}
