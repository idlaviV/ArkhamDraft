package arkhamDraft.UI.workerPool;

import arkhamDraft.SettingsManager;

public class GenerateBlacklistButtonWorker extends AbstractButtonWorker{
    private final SettingsManager settingsManager;

    public GenerateBlacklistButtonWorker(SettingsManager settingsManager) {
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
