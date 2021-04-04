package arkhamDraft.UI.workerPool;

import arkhamDraft.SettingsManager;

public class GenerateBlacklistWorker extends AbstractWorker {
    private final SettingsManager settingsManager;

    public GenerateBlacklistWorker(SettingsManager settingsManager) {
        super(null);
        this.settingsManager = settingsManager;
    }

    @Override
    protected void update() {

    }

    @Override
    protected Boolean doInBackground() throws Exception {
        settingsManager.generateDefaultBlacklist();
        return true;
    }
}
